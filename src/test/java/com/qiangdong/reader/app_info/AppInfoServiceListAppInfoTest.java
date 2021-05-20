package com.qiangdong.reader.app_info;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.AppInfo;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.AppInfoServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AppInfoServiceListAppInfoTest extends BaseTest {

	@Autowired
	private AppInfoServiceImpl appInfoService;

	@Test
	public void listApkVersionSuccessful() {
		PageResponse<AppInfo> response = appInfoService.listApkVersion(new BaseRequest());
		assertThat(response.getList().size()).isEqualTo(2);
	}
}