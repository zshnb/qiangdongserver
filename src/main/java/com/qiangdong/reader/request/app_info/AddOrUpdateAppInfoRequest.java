package com.qiangdong.reader.request.app_info;

import com.qiangdong.reader.annotation.NotNone;
import com.qiangdong.reader.enums.app_info.AppPlatformEnum;
import com.qiangdong.reader.enums.app_info.AppTypeEnum;
import com.qiangdong.reader.enums.app_info.UpdateTypeEnum;
import com.qiangdong.reader.request.BaseRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public class AddOrUpdateAppInfoRequest extends BaseRequest {
	private Long appInfoId = 0L;

	@Range(min = 1L, message = "版本号不能为0")
	private Integer versionCode = 0;

	@NotEmpty(message = "版本名称不能为空")
	private String versionName = "";

	@NotEmpty(message = "更新信息不能为空")
	private String versionInfo = "";

	@NotEmpty(message = "更新包链接不能为空")
	private String downloadUrl = "";

	@NotNone
	private AppTypeEnum type = AppTypeEnum.NONE;

	@NotNone
	private AppPlatformEnum platform = AppPlatformEnum.NONE;

	@NotNone
	private UpdateTypeEnum updateType = UpdateTypeEnum.NONE;

	public Long getAppInfoId() {
		return appInfoId;
	}

	public void setAppInfoId(Long appInfoId) {
		this.appInfoId = appInfoId;
	}

	public AppTypeEnum getType() {
		return type;
	}

	public void setType(AppTypeEnum type) {
		this.type = type;
	}

	public Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionInfo() {
		return versionInfo;
	}

	public void setVersionInfo(String versionInfo) {
		this.versionInfo = versionInfo;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public AppPlatformEnum getPlatform() {
		return platform;
	}

	public void setPlatform(AppPlatformEnum platform) {
		this.platform = platform;
	}

	public UpdateTypeEnum getUpdateType() {
		return updateType;
	}

	public void setUpdateType(UpdateTypeEnum updateType) {
		this.updateType = updateType;
	}
}
