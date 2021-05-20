package com.qiangdong.reader.activity_read_history;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.read_activity_record.ActivityReadHistoryDto;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.ActivityReadHistoryServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ActivityReadHistoryServiceListHistoryTest extends BaseTest {
	@Autowired
	private ActivityReadHistoryServiceImpl activityReadHistoryService;

	@Test
	public void listReadActivityRecordSuccessful(){
		BaseRequest request = new BaseRequest();
		request.setUserId(4L);
		PageResponse<ActivityReadHistoryDto> response = activityReadHistoryService.listHistory(request);
		assertThat(response.getList().get(0).getUserActivityId()).isEqualTo(1L);
		assertThat(response.getList().get(0).getUserId()).isEqualTo(4L);
		assertThat(response.getList().get(0).getContent()).isEqualTo("content1");
		assertThat(response.getList().get(0).getUsername()).isEqualTo("user1");
	}
}
