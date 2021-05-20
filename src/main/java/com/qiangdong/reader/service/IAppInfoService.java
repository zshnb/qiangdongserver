package com.qiangdong.reader.service;

import com.qiangdong.reader.entity.AppInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.app_info.AddOrUpdateAppInfoRequest;
import com.qiangdong.reader.request.app_info.DeleteAppInfoRequest;
import com.qiangdong.reader.request.app_info.GetAppInfoRequest;
import com.qiangdong.reader.request.app_info.GetLatestAppInfoRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-07
 */
public interface IAppInfoService extends IService<AppInfo> {

	PageResponse<AppInfo> listApkVersion(BaseRequest request);

	Response<AppInfo> getLatestAppInfo(GetLatestAppInfoRequest request);

	Response<AppInfo> getApkVersion(GetAppInfoRequest request, AppInfo appInfo);

	Response<AppInfo> addOrUpdateAppInfo(AddOrUpdateAppInfoRequest request);

	Response<String> deleteApkVersion(DeleteAppInfoRequest request, AppInfo appInfo);

    PageResponse<AppInfo> listUpdateLog(BaseRequest request);
}