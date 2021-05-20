package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.user.GetAuthorCenterRequest;
import com.qiangdong.reader.response.user.GetAuthorCenterResponse;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import java.time.Duration;
import java.time.Instant;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceGetAuthorCenterTest extends BaseTest {
	@Autowired
	private UserServiceImpl userService;

	@Test
	public void getNovelAuthorCenterSuccessful() {
		GetAuthorCenterRequest request = new GetAuthorCenterRequest();
		request.setUserId(1L);
		request.setType(WorksTypeEnum.NOVEL);
		GetAuthorCenterResponse response = userService.getAuthorCenter(request);
		assertThat(response.getCreateCountDay()).isEqualTo(
			(int)Duration.between(Instant.parse("2020-02-20T00:00:00.00Z"), Instant.now()).toDays());
		assertThat(response.getWriteCount()).isEqualTo(200001L);
		assertThat(response.getLastUpdateWorksSummaryDto().getName()).isEqualTo("novel1");
		assertThat(response.getLastUpdateWorksSummaryDto().getCommentCount()).isEqualTo(2);
		assertThat(response.getRecentUpdateNovels().size()).isEqualTo(2);
	}

	@Test
	public void getComicAuthorCenterSuccessful() {
		GetAuthorCenterRequest request = new GetAuthorCenterRequest();
		request.setUserId(1L);
		request.setType(WorksTypeEnum.COMIC);
		GetAuthorCenterResponse response = userService.getAuthorCenter(request);
		assertThat(response.getCreateCountDay()).isEqualTo(
			(int)Duration.between(Instant.parse("2020-02-20T00:00:00.00Z"), Instant.now()).toDays());
		assertThat(response.getLastUpdateWorksSummaryDto().getName()).isEqualTo("comic1");
		assertThat(response.getLastUpdateWorksSummaryDto().getCommentCount()).isEqualTo(2);
		assertThat(response.getRecentUpdateComics().size()).isEqualTo(2);
		assertThat(response.getWriteCount()).isEqualTo(2L);
	}
}
