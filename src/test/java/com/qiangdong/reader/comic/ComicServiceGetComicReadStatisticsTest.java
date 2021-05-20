package com.qiangdong.reader.comic;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.statistics.WorksReadStatisticsDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.enums.user.UserSexEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.serviceImpl.ComicServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicServiceGetComicReadStatisticsTest extends BaseTest {
	@Autowired
	private ComicServiceImpl comicService;

	@Test
	public void getComicStatisticsSuccessful() {
		WorksReadStatisticsDto worksReadStatisticsDto =
			comicService.getComicReadStatistics(getComicRequest, new Comic()).getData();
		assertThat(worksReadStatisticsDto.getReadUserCount()).isEqualTo(1);
		assertThat(worksReadStatisticsDto.getReadUserSexWithCounts().get(0).getSex()).isEqualTo(UserSexEnum.WOMAN);

		getComicRequest.setComicId(2L);
		worksReadStatisticsDto = comicService.getComicReadStatistics(getComicRequest, new Comic()).getData();
		assertThat(worksReadStatisticsDto.getReadUserCount()).isEqualTo(0);
	}

	@Test
	public void getComicStatisticsFailedWhenNotExist() {
		getComicRequest.setComicId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			comicService.getComicReadStatistics(getComicRequest, new Comic());
		});
	}

	@Test
	public void getComicStatisticsFailedWhenNoPermission() {
		getComicRequest.setUserId(-1L);
		assertException(PermissionDenyException.class, () -> {
			comicService.getComicReadStatistics(getComicRequest, new Comic());
		});
	}
}
