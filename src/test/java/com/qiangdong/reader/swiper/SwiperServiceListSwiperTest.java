package com.qiangdong.reader.swiper;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Swiper;
import com.qiangdong.reader.enums.type.TypeBelongEnum;
import com.qiangdong.reader.request.swiper.ListSwiperRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.SwiperServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SwiperServiceListSwiperTest extends BaseTest {
	@Autowired
	private SwiperServiceImpl swiperService;

	@Test
	public void listSuccessful() {
		ListSwiperRequest request = new ListSwiperRequest();
		request.setUserId(adminUserId);
		request.setTypeBelong(TypeBelongEnum.NOVEL);
		PageResponse<Swiper> response = swiperService.listSwiper(request);
		assertThat(response.getList().size()).isEqualTo(2);
	}
}
