package com.qiangdong.reader;

import cn.hutool.core.util.RandomUtil;
import com.qiangdong.reader.dto.WorksTagDto;
import com.qiangdong.reader.enums.app_info.AppPlatformEnum;
import com.qiangdong.reader.enums.app_info.AppTypeEnum;
import com.qiangdong.reader.enums.notice.NoticeTypeEnum;
import com.qiangdong.reader.enums.user_agreement.UserAgreementTypeEnum;
import com.qiangdong.reader.request.app_info.AddOrUpdateAppInfoRequest;
import com.qiangdong.reader.request.comic.AddOrUpdateComicChapterRequest;
import com.qiangdong.reader.request.comic.AddOrUpdateComicRequest;
import com.qiangdong.reader.request.comic.GetComicRequest;
import com.qiangdong.reader.request.help_feedback.AddOrUpdateHelpFeedbackRequest;
import com.qiangdong.reader.request.notice.AddOrUpdateNoticeRequest;
import com.qiangdong.reader.request.novel.AddOrUpdateNovelChapterRequest;
import com.qiangdong.reader.request.novel.AddOrUpdateNovelRequest;
import com.qiangdong.reader.request.novel.GetNovelRequest;
import com.qiangdong.reader.request.user_agreement.AddOrUpdateUserAgreementRequest;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = QiangDongApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
abstract public class BaseTest implements TestConstant {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private int db = 0;

	public static GetNovelRequest getNovelRequest;
	public static AddOrUpdateNovelRequest addNovelRequest;
	public static AddOrUpdateNovelChapterRequest addNovelChapterRequest;
	public static GetComicRequest getComicRequest;
	public static AddOrUpdateComicRequest addComicRequest;
	public static AddOrUpdateComicChapterRequest addComicChapterRequest;
	public static AddOrUpdateNoticeRequest addOrUpdateNoticeRequest;
	public static AddOrUpdateAppInfoRequest addOrUpdateAppInfoRequest;
	public static AddOrUpdateHelpFeedbackRequest addOrUpdateHelpFeedbackRequest;
	public static AddOrUpdateUserAgreementRequest addOrUpdateUserAgreementRequest;

	public BaseTest() {
		System.setProperty("es.set.netty.runtime.available.processors", "false");
	}

	@Before
	public void setUp() {
		List<WorksTagDto> tags = new ArrayList<>();
		WorksTagDto dto = new WorksTagDto();
		dto.setGroupName("风格");
		dto.setTagName("坚毅");
		tags.add(dto);

		getNovelRequest = new GetNovelRequest();
		getNovelRequest.setUserId(authorUserId);
		getNovelRequest.setNovelId(1L);

		addNovelRequest = new AddOrUpdateNovelRequest();
		addNovelRequest.setUserId(authorUserId);
		addNovelRequest.setName("test novel");
		addNovelRequest.setAuthorId(1L);
		addNovelRequest.setTypeId(1L);
		addNovelRequest.setTags(tags);

		addNovelChapterRequest = new AddOrUpdateNovelChapterRequest();
		addNovelChapterRequest.setUserId(authorUserId);
		addNovelChapterRequest.setNovelId(1L);
		addNovelChapterRequest.setTitle("title 1");

		getComicRequest = new GetComicRequest();
		getComicRequest.setUserId(authorUserId);
		getComicRequest.setComicId(1L);

		addComicRequest = new AddOrUpdateComicRequest();
		addComicRequest.setName("test comic");
		addComicRequest.setAuthorId(1L);
		addComicRequest.setTypeId(1L);
		addComicRequest.setTags(tags);

		addComicChapterRequest = new AddOrUpdateComicChapterRequest();
		addComicChapterRequest.setUserId(authorUserId);
		addComicChapterRequest.setComicId(1L);
		addComicChapterRequest.setTitle("title 1");
		addComicChapterRequest.setPictureCount(5);

		addOrUpdateNoticeRequest = new AddOrUpdateNoticeRequest();
		addOrUpdateNoticeRequest.setContent("test content");
		addOrUpdateNoticeRequest.setCover("test img");
		addOrUpdateNoticeRequest.setSubtitle("test subTitle");
		addOrUpdateNoticeRequest.setUserId(10L);
		addOrUpdateNoticeRequest.setType(NoticeTypeEnum.NOTICE_APP);
		addOrUpdateNoticeRequest.setTitle("test Title");

		addOrUpdateAppInfoRequest = new AddOrUpdateAppInfoRequest();
		addOrUpdateAppInfoRequest.setDownloadUrl("test url");
		addOrUpdateAppInfoRequest.setType(AppTypeEnum.TEST);
		addOrUpdateAppInfoRequest.setVersionName("test versionName");
		addOrUpdateAppInfoRequest.setVersionInfo("test content");
		addOrUpdateAppInfoRequest.setPlatform(AppPlatformEnum.IOS);

		addOrUpdateHelpFeedbackRequest = new AddOrUpdateHelpFeedbackRequest();
		addOrUpdateHelpFeedbackRequest.setContent("test content");

		addOrUpdateUserAgreementRequest = new AddOrUpdateUserAgreementRequest();
		addOrUpdateUserAgreementRequest.setUserId(adminUserId);
		addOrUpdateUserAgreementRequest.setContent("test content");
		addOrUpdateUserAgreementRequest.setEnabled(false);
		addOrUpdateUserAgreementRequest.setType(UserAgreementTypeEnum.USER_AGREEMENT);
		addOrUpdateUserAgreementRequest.setVersion("test versionName");

		Set<String> keys = redisTemplate.keys("*");
		if (keys != null) {
			redisTemplate.delete(keys);
		}

		Connection connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
		try {
			db = RandomUtil.randomInt(1, 100);
			jdbcTemplate.execute("create schema test_qd" + db);
			jdbcTemplate.execute("use test_qd" + db);
			Resource resource = new ClassPathResource("schema.sql");
			ScriptUtils.executeSqlScript(connection, resource);
			resource = new ClassPathResource("test-file/data.sql");
			ScriptUtils.executeSqlScript(connection, resource);
		} finally {
			DataSourceUtils.releaseConnection(connection, jdbcTemplate.getDataSource());
		}
	}

	@After
	public void afterClean() {
		jdbcTemplate.execute("drop schema test_qd" + db);
	}

	public void assertException(Class<?> exceptionClass, ExceptionRunner runner) {
		try {
			runner.run();
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals(exceptionClass, e.getClass());
		}
	}

	public <T extends Comparable<T> & Serializable> AssertAssumeBuilder<T> assertThat(T t) {
		return new AssertAssumeBuilder<>(t);
	}

	public <T extends Object & Comparable<T>> AssertAssumeBuilder<T> assertThat(Object object) {
		return new AssertAssumeBuilder<>(object, object);
	}
}