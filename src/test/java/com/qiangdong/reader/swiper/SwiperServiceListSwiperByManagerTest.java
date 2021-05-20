package com.qiangdong.reader.swiper;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.SwiperDto;
import com.qiangdong.reader.enums.type.TypeBelongEnum;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.swiper.ListSwiperRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.SwiperServiceImpl;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SwiperServiceListSwiperByManagerTest extends BaseTest {
	@Autowired
	private SwiperServiceImpl swiperService;

	@Test
	public void listSuccessful() {
		ListSwiperRequest request = new ListSwiperRequest();
		request.setUserId(adminUserId);
		request.setTypeBelong(TypeBelongEnum.NOVEL);
		PageResponse<SwiperDto> response = swiperService.listSwiperByManager(request);
		assertThat(response.getList().size()).isEqualTo(4);
	}

	@Test
	public void listFailedWhenPermissionDeny() {
		ListSwiperRequest request = new ListSwiperRequest();
		request.setUserId(userId);
		request.setTypeBelong(TypeBelongEnum.NOVEL);
		assertException(PermissionDenyException.class, () -> swiperService.listSwiperByManager(request));
	}
}
