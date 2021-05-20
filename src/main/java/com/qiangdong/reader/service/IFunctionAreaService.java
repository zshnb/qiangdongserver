package com.qiangdong.reader.service;

import com.qiangdong.reader.annotation.RequireAdmin;
import com.qiangdong.reader.entity.FunctionArea;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.function_area.AddOrUpdateFunctionAreaRequest;
import com.qiangdong.reader.request.function_area.DeleteFunctionAreaRequest;
import com.qiangdong.reader.request.function_area.ListByParentFunctionAreaRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-10
 */
public interface IFunctionAreaService extends IService<FunctionArea> {

	FunctionArea addOrUpdateFunctionArea(AddOrUpdateFunctionAreaRequest request);

	PageResponse<FunctionArea> listByParentFunctionArea(
		ListByParentFunctionAreaRequest request);

	@RequireAdmin
	PageResponse<FunctionArea> listParentFunctionArea(BaseRequest request);

	Response<String> deleteFunctionArea(DeleteFunctionAreaRequest request);
}
