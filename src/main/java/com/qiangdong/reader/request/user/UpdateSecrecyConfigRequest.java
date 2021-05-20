package com.qiangdong.reader.request.user;

import com.qiangdong.reader.dto.user.SecrecyConfig;
import com.qiangdong.reader.request.BaseRequest;

public class UpdateSecrecyConfigRequest extends BaseRequest {
	private SecrecyConfig secrecyConfig = new SecrecyConfig();

	public SecrecyConfig getSecrecyConfig() {
		return secrecyConfig;
	}

	public void setSecrecyConfig(SecrecyConfig secrecyConfig) {
		this.secrecyConfig = secrecyConfig;
	}
}
