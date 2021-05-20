package com.qiangdong.reader.request.user;

import com.qiangdong.reader.enums.user.UserSignStatusEnum;
import com.qiangdong.reader.request.BaseRequest;

public class UpdateAuthorByEditorRequest extends BaseRequest {

	private Long targetUserId = 0L;
	private String nickname = "";
	private UserSignStatusEnum userSignStatus = UserSignStatusEnum.NONE;
	private Long levelId = 0L;

	public Long getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(Long targetUserId) {
		this.targetUserId = targetUserId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public UserSignStatusEnum getUserSignStatus() {
		return userSignStatus;
	}

	public void setUserSignStatus(UserSignStatusEnum userSignStatus) {
		this.userSignStatus = userSignStatus;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}
}
