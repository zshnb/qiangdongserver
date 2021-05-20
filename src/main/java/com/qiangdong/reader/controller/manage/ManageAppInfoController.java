package com.qiangdong.reader.controller.manage;

import com.qiangdong.reader.entity.AppInfo;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.app_info.AddOrUpdateAppInfoRequest;
import com.qiangdong.reader.request.app_info.DeleteAppInfoRequest;
import com.qiangdong.reader.request.app_info.GetAppInfoRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IAppInfoService;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/app-info")
public class ManageAppInfoController {

	private final IAppInfoService apkVersionService;

	public ManageAppInfoController(IAppInfoService apkVersionService) {
		this.apkVersionService = apkVersionService;
	}

	@PostMapping("/list")
	public PageResponse<AppInfo> listApkVersion(@RequestBody BaseRequest request) {
		return apkVersionService.listApkVersion(request);
	}

	@PostMapping("/detail")
	public Response<AppInfo> getApkVersion(@RequestBody GetAppInfoRequest request) {
		return apkVersionService.getApkVersion(request, new AppInfo());
	}

	@PostMapping("/add-update")
	public Response<AppInfo> addOrUpdateApkVersion(@RequestBody @Valid AddOrUpdateAppInfoRequest request) {
		return apkVersionService.addOrUpdateAppInfo(request);
	}

	@DeleteMapping("/{appInfoId}")
	public Response<String> deleteApkVersion(@RequestBody DeleteAppInfoRequest request) {
		return apkVersionService.deleteApkVersion(request, new AppInfo());
	}
}