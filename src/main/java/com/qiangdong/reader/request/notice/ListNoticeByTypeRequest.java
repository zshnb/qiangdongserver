package com.qiangdong.reader.request.notice;

import com.qiangdong.reader.enums.notice.NoticeTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class ListNoticeByTypeRequest extends BaseRequest {

	private NoticeTypeEnum type = NoticeTypeEnum.NONE;

	public NoticeTypeEnum getType() {
		return type;
	}

	public void setType(NoticeTypeEnum type) {
		this.type = type;
	}
}
