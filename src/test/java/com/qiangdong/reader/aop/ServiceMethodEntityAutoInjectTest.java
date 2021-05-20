package com.qiangdong.reader.aop;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.type.DeleteTypeRequest;
import com.qiangdong.reader.serviceImpl.TypeServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

public class ServiceMethodEntityAutoInjectTest extends BaseTest {
	@Autowired
	private TypeServiceImpl typeService;

	@SpyBean
	private ServiceMethodEntityAutoInjectAop serviceMethodEntityAutoInjectAop;

	@Test
	public void injectEntitySuccessful() throws Throwable {
		DeleteTypeRequest request = new DeleteTypeRequest();
		request.setTypeId(1L);
		typeService.deleteType(request, new Type());
		verify(serviceMethodEntityAutoInjectAop, times(1)).entityInject(any());
	}

	@Test
	public void injectEntityFailedWhenEntityNotFound() throws Throwable {
		DeleteTypeRequest request = new DeleteTypeRequest();
		request.setTypeId(-1L);
		assertException(InvalidArgumentException.class, () -> typeService.deleteType(request, new Type()));
		verify(serviceMethodEntityAutoInjectAop, times(1)).entityInject(any());
	}
}
