package com.qiangdong.reader.app_info;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.AppInfo;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.serviceImpl.AppInfoServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AppInfoServiceAddOrUpdateAppInfoTest extends BaseTest {

	@Autowired
	private AppInfoServiceImpl appInfoService;

	@Test
	public void addAppInfoSuccessful() {
		AppInfo appInfo = appInfoService.addOrUpdateAppInfo(addOrUpdateAppInfoRequest).getData();
		assertThat(appInfo.getId()).isNotZero();
		assertThat(appInfo.getVersionInfo()).isEqualTo("test content");
		assertThat(appInfo.getVersionName()).isEqualTo("test versionName");
	}

	@Test
	public void addApkVersionFailedWhenVersionExist() {
		addOrUpdateAppInfoRequest.setVersionName("test name1");
		assertException(InvalidArgumentException.class, () -> {
			appInfoService.addOrUpdateAppInfo(addOrUpdateAppInfoRequest);
		});
	}

	@Test
	public void updateApkVersionSuccessful() {
		addOrUpdateAppInfoRequest.setAppInfoId(1L);
		addOrUpdateAppInfoRequest.setVersionInfo("update content");
		addOrUpdateAppInfoRequest.setVersionName("update versionName");
		AppInfo appInfo = appInfoService.addOrUpdateAppInfo(addOrUpdateAppInfoRequest)
			.getData();
		assertThat(appInfo.getVersionName()).isEqualTo("update versionName");
		assertThat(appInfo.getVersionInfo()).isEqualTo("update content");
	}

	@Test
	public void updateApkVersionFailedWhenIdIsInvalid() {
		addOrUpdateAppInfoRequest.setAppInfoId(100L);
		addOrUpdateAppInfoRequest.setVersionName("update versionName");
		assertException(InvalidArgumentException.class, () -> {
			appInfoService.addOrUpdateAppInfo(addOrUpdateAppInfoRequest);
		});
	}
}
