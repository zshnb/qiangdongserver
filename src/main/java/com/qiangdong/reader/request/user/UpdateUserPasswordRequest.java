package com.qiangdong.reader.request.user;

import com.qiangdong.reader.enums.common.VerifyWayEnum;
import com.qiangdong.reader.request.BaseRequest;

public class UpdateUserPasswordRequest extends BaseRequest {
	private String oldPassword = "";
	private String newPassword = "";
	private String verifyCode = "";
	private VerifyWayEnum verifyWay = VerifyWayEnum.NONE;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public VerifyWayEnum getVerifyWay() {
		return verifyWay;
	}

	public void setVerifyWay(VerifyWayEnum verifyWay) {
		this.verifyWay = verifyWay;
	}
}
