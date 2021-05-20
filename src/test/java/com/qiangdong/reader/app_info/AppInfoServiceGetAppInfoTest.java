package com.qiangdong.reader.app_info;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.AppInfo;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.app_info.GetAppInfoRequest;
import com.qiangdong.reader.serviceImpl.AppInfoServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AppInfoServiceGetAppInfoTest extends BaseTest {

	@Autowired
	private AppInfoServiceImpl appInfoService;

	@Test
	public void getApkVersionSuccessful() {
		GetAppInfoRequest request = new GetAppInfoRequest();
		request.setAppInfoId(1L);
		AppInfo appInfo = appInfoService.getApkVersion(request, new AppInfo()).getData();
		assertThat(appInfo).isNotNull();
		assertThat(appInfo.getVersionName()).isEqualTo("test name1");
		assertThat(appInfo.getVersionInfo()).isEqualTo("test content1");
		assertThat(appInfo.getDownloadUrl()).isEqualTo("test url1");
	}

	@Test
	public void getApkVersionFailedWhenIdIsInvalid() {
		GetAppInfoRequest request = new GetAppInfoRequest();
		request.setAppInfoId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			appInfoService.getApkVersion(request, new AppInfo());
		});
	}
}
