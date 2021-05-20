package com.qiangdong.reader.comic_chapter;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.common.ComicConstant;
import com.qiangdong.reader.dto.WorksRankingDto;
import com.qiangdong.reader.entity.ComicChapter;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.comic.GetComicChapterRequest;
import com.qiangdong.reader.schedule.WorksRankingSchedule;
import com.qiangdong.reader.serviceImpl.ComicChapterServiceImpl;
import com.qiangdong.reader.utils.DateUtil;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

public class ComicChapterServiceAddOrUpdateComicChapterTest extends BaseTest {
	@Autowired
	private ComicChapterServiceImpl comicChapterService;

	@Autowired
	private WorksRankingSchedule worksRankingSchedule;

	@Autowired
	private RedisTemplate<String, WorksRankingDto> redisTemplate;

	@SpyBean
	private DateUtil dateUtil;

	@Test
	public void addComicChapterSuccessful() {
		Mockito.when(dateUtil.getYesterday()).thenReturn(LocalDateTime.now());
		ComicChapter chapter = comicChapterService.addOrUpdateComicChapter(addComicChapterRequest);
		assertThat(chapter.getId()).isNotZero();
		assertThat(chapter.getTitle()).isEqualTo("title 1");
		assertThat(chapter.getIndex()).isEqualTo(3);

		worksRankingSchedule.calculateUpdateRanking();

		String key = ComicConstant.KEY_TOP_UPDATE_COMIC;
		ZSetOperations<String, WorksRankingDto> zSetOperations = redisTemplate.opsForZSet();
		Set<WorksRankingDto> worksRankingDtos = zSetOperations.reverseRangeByScore(key, 0, 7);
		assertThat(worksRankingDtos.size()).isEqualTo(2);
		assertThat(zSetOperations.score(key, worksRankingDtos.iterator().next())).isEqualTo(7.0);
	}

	@Test
	public void updateComicChapterSuccessful() {
		addComicChapterRequest.setChapterId(1L);
		comicChapterService.addOrUpdateComicChapter(addComicChapterRequest);

		GetComicChapterRequest getComicChapterRequest = new GetComicChapterRequest();
		getComicChapterRequest.setComicId(1L);
		getComicChapterRequest.setChapterId(1L);
		getComicChapterRequest.setUserId(authorUserId);
		ComicChapter chapter = comicChapterService.getComicChapter(getComicChapterRequest);
		Assert.assertEquals("title 1", chapter.getTitle());
	}

	@Test
	public void addComicChapterFailedWhenComicIdIsInvalid() {
		addComicChapterRequest.setComicId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			comicChapterService.addOrUpdateComicChapter(addComicChapterRequest);
		});
	}

	@Test
	public void addComicChapterFailedWhenDuplicateTitle() {
		comicChapterService.addOrUpdateComicChapter(addComicChapterRequest);
		assertException(InvalidArgumentException.class, () -> {
			comicChapterService.addOrUpdateComicChapter(addComicChapterRequest);
		});
	}

	@Test
	public void updateComicChapterFailedWhenComicIdIsInvalid() {
		addComicChapterRequest.setChapterId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			comicChapterService.addOrUpdateComicChapter(addComicChapterRequest);
		});
	}

	@Test
	public void updateComicChapterFailedWhenDuplicateTitle() {
		addComicChapterRequest.setChapterId(1L);
		addComicChapterRequest.setTitle("comic chapter 1-2");
		assertException(InvalidArgumentException.class, () -> {
			comicChapterService.addOrUpdateComicChapter(addComicChapterRequest);
		});
	}

	@Test
	public void updateComicChapterFailedWhenPermissionDeny() {
		addComicChapterRequest.setChapterId(1L);
		addComicChapterRequest.setUserId(userId);
		addComicChapterRequest.setTitle("comic chapter 1-5");
		assertException(PermissionDenyException.class, () -> {
			comicChapterService.addOrUpdateComicChapter(addComicChapterRequest);
		});
	}
}
