package com.qiangdong.reader.controller;


import com.qiangdong.reader.entity.FunctionArea;
import com.qiangdong.reader.request.function_area.ListByParentFunctionAreaRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.FunctionAreaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/function-area")
public class FunctionAreaController {
	@Autowired
	private FunctionAreaServiceImpl functionAreaService;

	@PostMapping("/list")
	public PageResponse<FunctionArea> listByParentFunctionArea(
		@RequestBody ListByParentFunctionAreaRequest request) {
		return functionAreaService.listByParentFunctionArea(request);
	}
}
