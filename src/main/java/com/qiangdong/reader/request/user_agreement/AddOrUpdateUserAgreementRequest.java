package com.qiangdong.reader.request.user_agreement;

import com.qiangdong.reader.annotation.NotNone;
import com.qiangdong.reader.enums.user_agreement.UserAgreementTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class AddOrUpdateUserAgreementRequest extends BaseRequest {
	private Long userAgreementId = 0L;
	private String version = "";
	private String content = "";
	private Boolean enabled = false;
	@NotNone(message = "无效的协议类型")
	private UserAgreementTypeEnum type = UserAgreementTypeEnum.NONE;

	public Long getUserAgreementId() {
		return userAgreementId;
	}

	public void setUserAgreementId(Long userAgreementId) {
		this.userAgreementId = userAgreementId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public UserAgreementTypeEnum getType() {
		return type;
	}

	public void setType(UserAgreementTypeEnum type) {
		this.type = type;
	}
}
