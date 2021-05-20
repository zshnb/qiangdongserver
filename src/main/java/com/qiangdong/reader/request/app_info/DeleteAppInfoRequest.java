package com.qiangdong.reader.request.app_info;

import com.qiangdong.reader.request.BaseRequest;

public class DeleteAppInfoRequest extends BaseRequest {

	private Long appInfoId = 0L;

	public Long getAppInfoId() {
		return appInfoId;
	}

	public void setAppInfoId(Long appInfoId) {
		this.appInfoId = appInfoId;
	}
}
