package com.qiangdong.reader.function_area;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.FunctionArea;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.function_area.DeleteFunctionAreaRequest;
import com.qiangdong.reader.request.function_area.ListByParentFunctionAreaRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.FunctionAreaServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FunctionAreaServiceDeleteFunctionAreaTest extends BaseTest {
	@Autowired
	private FunctionAreaServiceImpl functionAreaService;

	@Test
	public void deleteSuccessful() {
		DeleteFunctionAreaRequest request = new DeleteFunctionAreaRequest();
		request.setUserId(adminUserId);
		request.setFunctionAreaId(3L);
		functionAreaService.deleteFunctionArea(request);

		ListByParentFunctionAreaRequest listByParentFunctionAreaRequest = new ListByParentFunctionAreaRequest();
		listByParentFunctionAreaRequest.setParentId(1L);
		PageResponse<FunctionArea> response =
			functionAreaService.listByParentFunctionArea(listByParentFunctionAreaRequest);
		assertThat(response.getList().size()).isEqualTo(0);
	}

	@Test
	public void deleteFailedWhenNoExist() {
		DeleteFunctionAreaRequest request = new DeleteFunctionAreaRequest();
		request.setUserId(adminUserId);
		request.setFunctionAreaId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			functionAreaService.deleteFunctionArea(request);
		});
	}

	@Test
	public void deleteFailedWhenHasChildrenFunctionArea() {
		DeleteFunctionAreaRequest request = new DeleteFunctionAreaRequest();
		request.setUserId(adminUserId);
		request.setFunctionAreaId(1L);
		assertException(InvalidArgumentException.class, () -> {
			functionAreaService.deleteFunctionArea(request);
		});
	}
}
