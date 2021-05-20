package com.qiangdong.reader.notice;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Notice;
import com.qiangdong.reader.enums.notice.NoticeTypeEnum;
import com.qiangdong.reader.request.notice.ListNoticeByTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.NoticeServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NoticeServiceListNoticeByTypeTest extends BaseTest {

	@Autowired
	private NoticeServiceImpl noticeService;

	@Test
	public void listNoticeByTypeSuccessful() {
		ListNoticeByTypeRequest listNoticeByTypeRequest = new ListNoticeByTypeRequest();
		listNoticeByTypeRequest.setType(NoticeTypeEnum.NOTICE_APP);
		listNoticeByTypeRequest.setUserId(10L);
		PageResponse<Notice> response = noticeService.listNoticeByType(listNoticeByTypeRequest);
		assertThat(response.getList().size()).isEqualTo(1);
		Notice notice = response.getList().get(0);
		assertThat(notice.getTitle()).isEqualTo("test Title2");
	}
}