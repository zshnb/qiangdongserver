package com.qiangdong.reader.controller;


import com.qiangdong.reader.entity.AppInfo;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.app_info.GetLatestAppInfoRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.AppInfoServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-07
 */
@RestController
@RequestMapping("/app-info")
public class AppInfoController {
    private final AppInfoServiceImpl appInfoService;

    public AppInfoController(AppInfoServiceImpl appInfoService) {
        this.appInfoService = appInfoService;
    }

    @PostMapping("/latest")
    public Response<AppInfo> getLatestAppInfo(@RequestBody GetLatestAppInfoRequest request) {
        return appInfoService.getLatestAppInfo(request);
    }

    @PostMapping("/log")
    public PageResponse<AppInfo> listUpdateLog(@RequestBody BaseRequest request) {
        return appInfoService.listUpdateLog(request);
    }
}
