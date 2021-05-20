package com.qiangdong.reader.controller.manage;


import com.qiangdong.reader.entity.FunctionArea;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.function_area.AddOrUpdateFunctionAreaRequest;
import com.qiangdong.reader.request.function_area.DeleteFunctionAreaRequest;
import com.qiangdong.reader.request.function_area.ListByParentFunctionAreaRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.FunctionAreaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
 * @since 2020-06-10
 */
@RestController
@RequestMapping("/manage/function-area")
public class ManageFunctionAreaController {
	@Autowired
	private FunctionAreaServiceImpl functionAreaService;

	@PostMapping("/add-update")
	public Response<FunctionArea> addOrUpdateFunctionArea(
		@RequestBody AddOrUpdateFunctionAreaRequest request) {
		return Response.ok(functionAreaService.addOrUpdateFunctionArea(request));
	}

	@PostMapping("/list")
	public PageResponse<FunctionArea> listByParentFunctionArea(
		@RequestBody ListByParentFunctionAreaRequest request) {
		return functionAreaService.listByParentFunctionArea(request);
	}

	@PostMapping("/list-parent-area")
	public PageResponse<FunctionArea> listParentArea(@RequestBody BaseRequest request) {
		return functionAreaService.listParentFunctionArea(request);
	}

	@DeleteMapping("/{functionAreaId}")
	public Response<String> deleteFunctionArea(@RequestBody DeleteFunctionAreaRequest request) {
		return functionAreaService.deleteFunctionArea(request);
	}
}
