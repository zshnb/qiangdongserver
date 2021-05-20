package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.entity.AppInfo;
import com.qiangdong.reader.dao.AppInfoMapper;
import com.qiangdong.reader.enums.app_info.AppPlatformEnum;
import com.qiangdong.reader.enums.app_info.AppTypeEnum;
import com.qiangdong.reader.enums.app_info.UpdateTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.app_info.AddOrUpdateAppInfoRequest;
import com.qiangdong.reader.request.app_info.DeleteAppInfoRequest;
import com.qiangdong.reader.request.app_info.GetAppInfoRequest;
import com.qiangdong.reader.request.app_info.GetLatestAppInfoRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IAppInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-07
 */
@Service
public class AppInfoServiceImpl extends ServiceImpl<AppInfoMapper, AppInfo> implements IAppInfoService {
	private final PageUtil pageUtil;

	public AppInfoServiceImpl(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}

	@Override
	public PageResponse<AppInfo> listApkVersion(BaseRequest request) {
		QueryWrapper<AppInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByAsc("create_at");
		IPage<AppInfo> apkVersionList = page(pageUtil.of(request), queryWrapper);
		return PageResponse.of(apkVersionList, request.getPageSize());
	}

	@Override
	public Response<AppInfo> getLatestAppInfo(GetLatestAppInfoRequest request) {
		if (request.getPlatform().equals(AppPlatformEnum.ANDROID)) {
			if (request.getVersionCode() == 0) {
				AppInfo resourceInfo = getOne(new QueryWrapper<AppInfo>()
					.eq("platform", request.getPlatform())
					.orderByDesc("create_at")
					.last("limit 1"));
				return Response.ok(resourceInfo);
			}
			AppInfo appInfo = getOne(new QueryWrapper<AppInfo>()
				.eq("platform", request.getPlatform())
                .eq("update_type", UpdateTypeEnum.UPDATE_APP)
				.gt("version_code", request.getVersionCode())
				.orderByDesc("create_at")
				.last("limit 1"));
			if (appInfo == null) {
				AppInfo resourceInfo = getOne(new QueryWrapper<AppInfo>()
					.eq("platform", request.getPlatform())
					.eq("update_type", UpdateTypeEnum.UPDATE_RESOURCE)
					.orderByDesc("create_at")
					.last("limit 1"));
				return Response.ok(resourceInfo);
			}
			return Response.ok(appInfo);
		}
		return Response.ok(null);
	}

	@Override
	public Response<AppInfo> getApkVersion(GetAppInfoRequest request, AppInfo appInfo) {
		return Response.ok(appInfo);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<AppInfo> addOrUpdateAppInfo(AddOrUpdateAppInfoRequest request) {
		AppInfo sameVersion = getOne(new QueryWrapper<AppInfo>()
			.eq("version_name", request.getVersionName()));
		AssertUtil.assertNull(sameVersion, "app版本已存在");

		AppInfo appInfo;
		if (request.getAppInfoId() == 0L) {
			appInfo = new AppInfo();
			BeanUtils.copyProperties(request, appInfo);
			save(appInfo);
		} else {
			appInfo = getById(request.getAppInfoId());
			AssertUtil.assertNotNull(appInfo, "app版本不存在");
			BeanUtils.copyProperties(request, appInfo);
			updateById(appInfo);
		}
		return Response.ok(appInfo);
	}

	@Override
	public Response<String> deleteApkVersion(DeleteAppInfoRequest request, AppInfo appInfo) {
		removeById(appInfo.getId());
		return Response.ok();
	}

	@Override
	public PageResponse<AppInfo> listUpdateLog(BaseRequest request) {
		IPage<AppInfo> appInfoIPage = page(pageUtil.of(request), new QueryWrapper<AppInfo>()
			.select("version_name", "version_info", "create_at")
			.orderByDesc("create_at"));
		return PageResponse.of(appInfoIPage, request.getPageSize());
	}
}
