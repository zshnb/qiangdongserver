package com.qiangdong.reader.request.notice;

import com.qiangdong.reader.annotation.NotNone;
import com.qiangdong.reader.enums.notice.NoticeTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class AddOrUpdateNoticeRequest extends BaseRequest {

	private Long noticeId = 0L;
	private String title = "";
	private String subtitle = "";
	private String content = "";
	private String cover = "";
	@NotNone(message = "无效的公告类型")
	private NoticeTypeEnum type = NoticeTypeEnum.NONE;

	public NoticeTypeEnum getType() {
		return type;
	}

	public void setType(NoticeTypeEnum type) {
		this.type = type;
	}

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}
}
