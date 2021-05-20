package com.qiangdong.reader.request.user;

import com.qiangdong.reader.request.BaseRequest;

public class UpdateUserEmailRequest extends BaseRequest {
	private String email = "";

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
