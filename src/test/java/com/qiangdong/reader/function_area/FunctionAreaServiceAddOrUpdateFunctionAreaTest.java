package com.qiangdong.reader.function_area;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.FunctionArea;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.function_area.AddOrUpdateFunctionAreaRequest;
import com.qiangdong.reader.serviceImpl.FunctionAreaServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FunctionAreaServiceAddOrUpdateFunctionAreaTest extends BaseTest {
	@Autowired
	private FunctionAreaServiceImpl functionAreaService;

	@Test
	public void addFunctionAreaSuccessful() {
		AddOrUpdateFunctionAreaRequest request = new AddOrUpdateFunctionAreaRequest();
		request.setUserId(adminUserId);
		request.setName("美食");
		FunctionArea functionArea = functionAreaService.addOrUpdateFunctionArea(request);
		Assert.assertEquals("美食", functionArea.getName());
		Assert.assertThat(functionArea.getId(), Matchers.greaterThan(0L));
	}

	@Test
	public void updateFunctionAreaSuccessful() {
		AddOrUpdateFunctionAreaRequest request = new AddOrUpdateFunctionAreaRequest();
		request.setUserId(adminUserId);
		request.setName("美食");
		FunctionArea functionArea = functionAreaService.addOrUpdateFunctionArea(request);
		Assert.assertEquals("美食", functionArea.getName());
		Assert.assertThat(functionArea.getId(), Matchers.greaterThan(0L));

		request.setFunctionAreaId(functionArea.getId());
		request.setName("节日");
		functionArea = functionAreaService.addOrUpdateFunctionArea(request);
		Assert.assertEquals("节日", functionArea.getName());
		Assert.assertThat(functionArea.getId(), Matchers.greaterThan(0L));
	}

	@Test
	public void addFunctionAreaFailedWhenNameExist() {
		AddOrUpdateFunctionAreaRequest request = new AddOrUpdateFunctionAreaRequest();
		request.setUserId(adminUserId);
		request.setName("美食");
		functionAreaService.addOrUpdateFunctionArea(request);
		assertException(InvalidArgumentException.class, () -> {
			functionAreaService.addOrUpdateFunctionArea(request);
		});
	}

	@Test
	public void updateFunctionAreaFailedWhenNameExist() {
		AddOrUpdateFunctionAreaRequest request = new AddOrUpdateFunctionAreaRequest();
		request.setUserId(adminUserId);
		request.setFunctionAreaId(1L);
		request.setName("功能2");
		assertException(InvalidArgumentException.class, () -> {
			functionAreaService.addOrUpdateFunctionArea(request);
		});

	}
}
