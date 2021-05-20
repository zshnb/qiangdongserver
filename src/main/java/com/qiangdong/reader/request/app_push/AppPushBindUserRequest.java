package com.qiangdong.reader.request.app_push;

import com.qiangdong.reader.request.BaseRequest;

public class AppPushBindUserRequest extends BaseRequest {

	private String clientId = "";
	private String tag = "";

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
