package com.qiangdong.reader.request.app_info;

import com.qiangdong.reader.enums.app_info.AppPlatformEnum;
import com.qiangdong.reader.request.BaseRequest;

public class GetLatestAppInfoRequest extends BaseRequest {
    private AppPlatformEnum platform = AppPlatformEnum.NONE;
    private Integer versionCode = 0;

    public AppPlatformEnum getPlatform() {
        return platform;
    }

    public void setPlatform(AppPlatformEnum platform) {
        this.platform = platform;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }
}
