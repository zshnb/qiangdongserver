package com.qiangdong.reader.swiper;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.SwiperDto;
import com.qiangdong.reader.entity.Swiper;
import com.qiangdong.reader.enums.type.TypeBelongEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.swiper.DeleteSwiperRequest;
import com.qiangdong.reader.request.swiper.ListSwiperRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.SwiperServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SwiperServiceDeleteSwiperTest extends BaseTest {
	@Autowired
	private SwiperServiceImpl swiperService;

	@Test
	public void deleteSwiperSuccessful() {
		DeleteSwiperRequest request = new DeleteSwiperRequest();
		request.setUserId(adminUserId);
		request.setSwiperId(1L);
		swiperService.deleteSwiper(request, new Swiper());

		ListSwiperRequest listSwiperRequest = new ListSwiperRequest();
		listSwiperRequest.setUserId(adminUserId);
		listSwiperRequest.setTypeBelong(TypeBelongEnum.COMIC);
		PageResponse<SwiperDto> response = swiperService.listSwiperByManager(listSwiperRequest);
		assertThat(response.getList().size()).isEqualTo(3);
	}

	@Test
	public void deleteFailedWhenSwiperNotExist() {
		assertException(InvalidArgumentException.class, () -> {
			DeleteSwiperRequest request = new DeleteSwiperRequest();
			request.setUserId(adminUserId);
			request.setSwiperId(-1L);
			swiperService.deleteSwiper(request, new Swiper());
		});
	}
}
