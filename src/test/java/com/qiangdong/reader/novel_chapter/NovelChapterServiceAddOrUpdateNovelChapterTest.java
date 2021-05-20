package com.qiangdong.reader.novel_chapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qcloud.cos.COSClient;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.common.NovelConstant;
import com.qiangdong.reader.config.TencentOssConfig;
import com.qiangdong.reader.dto.WorksRankingDto;
import com.qiangdong.reader.entity.NovelChapter;
import com.qiangdong.reader.enums.common.LoginPlatformEnum;
import com.qiangdong.reader.enums.common.WorksChapterTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.interceptor.JwtInterceptor;
import com.qiangdong.reader.request.novel.GetNovelChapterRequest;
import com.qiangdong.reader.request.user.EnableAuthorAllowChargeRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.schedule.WorksRankingSchedule;
import com.qiangdong.reader.serviceImpl.NovelChapterServiceImpl;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import com.qiangdong.reader.utils.DateUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Set;

import com.qiangdong.reader.utils.JwtUtil;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class NovelChapterServiceAddOrUpdateNovelChapterTest extends BaseTest {
	@Autowired
	private NovelChapterServiceImpl novelChapterService;

	@Autowired
	private RedisTemplate<String, WorksRankingDto> redisTemplate;

	@Autowired
	private WorksRankingSchedule worksRankingSchedule;

	@Autowired
	private UserServiceImpl userService;

	@SpyBean
	private DateUtil dateUtil;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private COSClient cosClient;

	@Autowired
	private TencentOssConfig ossConfig;

	@Test
	public void addNovelChapterSuccessful() {
		EnableAuthorAllowChargeRequest enableAuthorAllowChargeRequest = new EnableAuthorAllowChargeRequest();
		enableAuthorAllowChargeRequest.setUserId(editorUserId);
		enableAuthorAllowChargeRequest.setAuthorId(authorUserId);
		userService.enableAuthorAllowCharge(enableAuthorAllowChargeRequest);

		Mockito.when(dateUtil.getYesterday()).thenReturn(LocalDateTime.now());
		addNovelChapterRequest.setWordCount(1320);
		addNovelChapterRequest.setType(WorksChapterTypeEnum.VIP);
		NovelChapter chapter = novelChapterService.addOrUpdateNovelChapter(addNovelChapterRequest);
		Assert.assertNotEquals(0L, chapter.getId().longValue());
		assertThat(chapter.getTitle()).isEqualTo("title 1");
		assertThat(chapter.getIndex()).isEqualTo(3);
		assertThat(chapter.getPrice()).isEqualTo(0.7);

		worksRankingSchedule.calculateUpdateRanking();

		String key = String.format("%s-1", NovelConstant.KEY_TOP_UPDATE_NOVEL);
		ZSetOperations<String, WorksRankingDto> zSetOperations = redisTemplate.opsForZSet();
		Set<WorksRankingDto> worksRankingDtos = zSetOperations.reverseRangeByScore(key, 0, 2000);
		assertThat(worksRankingDtos.size()).isEqualTo(2);
		assertThat(zSetOperations.score(key, worksRankingDtos.iterator().next())).isEqualTo(1330.0);
	}

	@Test
	public void updateNovelChapterSuccessful() {
		addNovelChapterRequest.setChapterId(1L);
		novelChapterService.addOrUpdateNovelChapter(addNovelChapterRequest);

		GetNovelChapterRequest getNovelChapterRequest = new GetNovelChapterRequest();
		getNovelChapterRequest.setNovelId(1L);
		getNovelChapterRequest.setChapterId(1L);
		getNovelChapterRequest.setUserId(authorUserId);
		NovelChapter chapter = novelChapterService.getNovelChapter(getNovelChapterRequest);
		Assert.assertEquals("title 1", chapter.getTitle());
	}

	@Test
	public void addNovelChapterFailedWhenNovelIdIsInvalid() {
		addNovelChapterRequest.setNovelId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			novelChapterService.addOrUpdateNovelChapter(addNovelChapterRequest);
		});
	}

	@Test(expected = InvalidArgumentException.class)
	public void addNovelChapterFailedWhenDuplicateTitle() {
		novelChapterService.addOrUpdateNovelChapter(addNovelChapterRequest);
		novelChapterService.addOrUpdateNovelChapter(addNovelChapterRequest);
	}

	@Test(expected = InvalidArgumentException.class)
	public void updateNovelChapterFailedWhenNovelIdIsInvalid() {
		addNovelChapterRequest.setChapterId(-1L);
		novelChapterService.addOrUpdateNovelChapter(addNovelChapterRequest);
	}

	@Test(expected = InvalidArgumentException.class)
	public void updateNovelChapterFailedWhenDuplicateTitle() {
		addNovelChapterRequest.setChapterId(1L);
		addNovelChapterRequest.setTitle("novel chapter 2");
		novelChapterService.addOrUpdateNovelChapter(addNovelChapterRequest);
	}

	@Test
	public void addChapterFailedWhenPermissionDeny() {
		addNovelChapterRequest.setUserId(userId);
		assertException(PermissionDenyException.class, () ->
			novelChapterService.addOrUpdateNovelChapter(addNovelChapterRequest));
	}

	@Test
	public void addChapterFailedWhenTooMuchBadWord() throws Exception {
		String token = jwtUtil.signJwtToken(1L, LoginPlatformEnum.APP.description());
		String fileName = "./src/test/resources/test-file/novel1-chapter1.txt";
		byte[] sourceFileBytes = Files.readAllBytes(Paths.get(fileName));

		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.multipart("/upload/novel/single-chapter")
						.file(new MockMultipartFile("file", fileName, "text/plain", sourceFileBytes))
						.param("novelId", "1")
						.header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP)
						.header(JwtInterceptor.HEADER_AUTHENTICATION, token))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andReturn();
		String body = mvcResult.getResponse().getContentAsString();
		Response<String> response = new Gson().fromJson(body, new TypeToken<Response<String>>(){}.getType());
		addNovelChapterRequest.setTxtUrl(response.getData());
		assertException(InvalidArgumentException.class, () ->
			novelChapterService.addOrUpdateNovelChapter(addNovelChapterRequest)
		);

		cosClient.deleteObject(ossConfig.getNovelBucketName(),
				response.getData().substring(response.getData().indexOf("/1")).substring(1));
		cosClient.shutdown();
	}
}
