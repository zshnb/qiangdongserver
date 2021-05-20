package com.qiangdong.reader.function_area;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.FunctionArea;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.function_area.ListByParentFunctionAreaRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.FunctionAreaServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FunctionAreaServiceListParentFunctionAreaTest extends BaseTest {
	@Autowired
	private FunctionAreaServiceImpl functionAreaService;

	@Test
	public void listSuccessful() {
		BaseRequest request = new BaseRequest();
		request.setUserId(adminUserId);
		PageResponse<FunctionArea> response = functionAreaService.listParentFunctionArea(request);
		assertThat(response.getList().size()).isEqualTo(2);
	}
}
