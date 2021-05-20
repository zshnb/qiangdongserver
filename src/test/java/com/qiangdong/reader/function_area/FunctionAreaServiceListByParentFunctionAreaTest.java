package com.qiangdong.reader.function_area;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.FunctionArea;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.function_area.ListByParentFunctionAreaRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.FunctionAreaServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FunctionAreaServiceListByParentFunctionAreaTest extends BaseTest {
	@Autowired
	private FunctionAreaServiceImpl functionAreaService;

	@Test
	public void listSuccessful() {
		ListByParentFunctionAreaRequest request = new ListByParentFunctionAreaRequest();
		request.setParentId(1L);
		PageResponse<FunctionArea> response = functionAreaService.listByParentFunctionArea(request);
		assertThat(response.getList().size()).isEqualTo(1);
	}

	@Test
	public void listFailedWhenParentFunctionAreaNoExist() {
		ListByParentFunctionAreaRequest request = new ListByParentFunctionAreaRequest();
		request.setParentId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			functionAreaService.listByParentFunctionArea(request);
		});
	}
}
