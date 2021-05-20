package com.qiangdong.reader.notice;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Notice;
import com.qiangdong.reader.enums.notice.NoticeTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.notice.DeleteNoticeRequest;
import com.qiangdong.reader.request.notice.ListNoticeByTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.NoticeServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NoticeServiceDeleteTest extends BaseTest {

	@Autowired
	private NoticeServiceImpl noticeService;

	@Test
	public void deleteNoticeSuccessful() {
		DeleteNoticeRequest request = new DeleteNoticeRequest();
		request.setNoticeId(1L);
		noticeService.deleteNotice(request, new Notice());
		Notice notice = noticeService.getById(1L);
		assertThat(notice).isNull();
	}


	@Test
	public void deleteNoticeFailedWhenNoticeIsInvalid() {
		DeleteNoticeRequest request = new DeleteNoticeRequest();
		request.setNoticeId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			noticeService.deleteNotice(request, new Notice());
		});
	}
}
