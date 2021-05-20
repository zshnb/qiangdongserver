package com.qiangdong.reader.notice;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Notice;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.notice.GetNoticeRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.NoticeServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NoticeServiceGetNoticeTest extends BaseTest {

	@Autowired
	private NoticeServiceImpl noticeService;

	@Test
	public void getNoticeSuccessful() {
		GetNoticeRequest request = new GetNoticeRequest();
		request.setNoticeId(2L);
		Response<Notice> response = noticeService.getNotice(request, new Notice());
		assertThat(response.getData().getTitle()).isEqualTo("test Title2");
	}

	@Test
	public void getNoticeFailedWhenIdIsInvalid() {
		GetNoticeRequest request = new GetNoticeRequest();
		request.setNoticeId(0L);
		assertException(InvalidArgumentException.class, () -> {
			noticeService.getNotice(request, new Notice());
		});
	}
}
