package com.qiangdong.reader.app_info;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.AppInfo;
import com.qiangdong.reader.enums.app_info.AppPlatformEnum;
import com.qiangdong.reader.request.app_info.GetLatestAppInfoRequest;
import com.qiangdong.reader.serviceImpl.AppInfoServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AppInfoServiceGetLatestAppInfoTest extends BaseTest {

	@Autowired
	private AppInfoServiceImpl appInfoService;

	@Test
	public void getApkVersionSuccessful() {
		GetLatestAppInfoRequest request = new GetLatestAppInfoRequest();
		request.setPlatform(AppPlatformEnum.ANDROID);
		request.setVersionCode(0);
		AppInfo appInfo = appInfoService.getLatestAppInfo(request).getData();
		assertThat(appInfo).isNotNull();
		assertThat(appInfo.getVersionName()).isEqualTo("test name1");
		assertThat(appInfo.getVersionInfo()).isEqualTo("test content1");
		assertThat(appInfo.getDownloadUrl()).isEqualTo("test url1");
	}
}
