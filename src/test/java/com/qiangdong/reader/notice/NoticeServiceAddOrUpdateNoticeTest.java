package com.qiangdong.reader.notice;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Notice;
import com.qiangdong.reader.enums.notice.NoticeTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.serviceImpl.NoticeServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NoticeServiceAddOrUpdateNoticeTest extends BaseTest {

	@Autowired
	private NoticeServiceImpl noticeService;

	@Test
	public void addNoticeSuccessful() {
		Notice notice = noticeService.addOrUpdateNotice(addOrUpdateNoticeRequest).getData();
		assertThat(notice.getId()).isNotZero();
		assertThat(notice.getTitle()).isEqualTo("test Title");
		assertThat(notice.getType()).isEqualTo(NoticeTypeEnum.NOTICE_APP);
	}

	@Test
	public void updateNoticeSuccessful() {
		addOrUpdateNoticeRequest.setNoticeId(2L);
		addOrUpdateNoticeRequest.setTitle("update Title");
		addOrUpdateNoticeRequest.setContent("update Content");
		Notice notice = noticeService.addOrUpdateNotice(addOrUpdateNoticeRequest).getData();
		assertThat(notice.getContent()).isEqualTo("update Content");
		assertThat(notice.getTitle()).isEqualTo("update Title");
	}

	@Test
	public void updateNoticeFailedWhenNoticeIdIsInvalid() {
		addOrUpdateNoticeRequest.setNoticeId(100L);
		addOrUpdateNoticeRequest.setTitle("update Title");
		addOrUpdateNoticeRequest.setContent("update Content");
		assertException(InvalidArgumentException.class, () -> {
			noticeService.addOrUpdateNotice(addOrUpdateNoticeRequest);
		});
	}
}
