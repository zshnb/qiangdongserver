package com.qiangdong.reader.app_info;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.AppInfo;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.app_info.DeleteAppInfoRequest;
import com.qiangdong.reader.serviceImpl.AppInfoServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AppInfoServiceDeleteAppInfoTest extends BaseTest {

	@Autowired
	private AppInfoServiceImpl appInfoService;

	@Test
	public void deleteApkVersionSuccessful() {
		DeleteAppInfoRequest deleteAppInfoRequest = new DeleteAppInfoRequest();
		deleteAppInfoRequest.setAppInfoId(1L);
		appInfoService.deleteApkVersion(deleteAppInfoRequest, new AppInfo());
		AppInfo appInfo = appInfoService.getById(1L);
		assertThat(appInfo).isNull();
	}

	@Test
	public void deleteApkVersionFailedWhenIdIsInvalid() {
		DeleteAppInfoRequest deleteAppInfoRequest = new DeleteAppInfoRequest();
		deleteAppInfoRequest.setAppInfoId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			appInfoService.deleteApkVersion(deleteAppInfoRequest, new AppInfo());
		});
	}
}
