package com.qiangdong.reader.novel;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.statistics.WorksReadStatisticsDto;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.enums.user.UserSexEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelServiceGetNovelReadStatisticsTest extends BaseTest {
	@Autowired
	private NovelServiceImpl novelService;

	@Test
	public void getNovelStatisticsSuccessful() {
		WorksReadStatisticsDto worksReadStatisticsDto =
			novelService.getNovelReadStatistics(getNovelRequest, new Novel()).getData();
		assertThat(worksReadStatisticsDto.getReadUserCount()).isEqualTo(2);
		assertThat(worksReadStatisticsDto.getReadUserSexWithCounts().get(0).getSex()).isEqualTo(UserSexEnum.WOMAN);

		getNovelRequest.setNovelId(2L);
		worksReadStatisticsDto = novelService.getNovelReadStatistics(getNovelRequest, new Novel()).getData();
		assertThat(worksReadStatisticsDto.getReadUserCount()).isEqualTo(1);
	}

	@Test
	public void getNovelStatisticsFailedWhenNotExist() {
		getNovelRequest.setNovelId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			novelService.getNovelReadStatistics(getNovelRequest, new Novel());
		});
	}

	@Test
	public void getNovelStatisticsFailedWhenNoPermission() {
		getNovelRequest.setUserId(-1L);
		assertException(PermissionDenyException.class, () -> {
			novelService.getNovelReadStatistics(getNovelRequest, new Novel());
		});
	}
}
