package com.qiangdong.reader.swiper;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Swiper;
import com.qiangdong.reader.enums.swiper.LinkTypeEnum;
import com.qiangdong.reader.enums.type.TypeBelongEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.swiper.AddOrUpdateSwiperRequest;
import com.qiangdong.reader.serviceImpl.SwiperServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SwiperServiceAddOrUpdateSwiperTest extends BaseTest {
	@Autowired
	private SwiperServiceImpl swiperService;

	@Test
	public void addSwiperSuccessful() {
		AddOrUpdateSwiperRequest request = new AddOrUpdateSwiperRequest();
		request.setUserId(adminUserId);
		request.setType(TypeBelongEnum.NOVEL);
		request.setItemId(2L);
		request.setLinkType(LinkTypeEnum.INNER);
		Swiper swiper = swiperService.addOrUpdateSwiper(request).getData();
		assertThat(swiper.getId()).isNotZero();
	}

	@Test
	public void updateSwiperSuccessful() {
		AddOrUpdateSwiperRequest request = new AddOrUpdateSwiperRequest();
		request.setUserId(adminUserId);
		request.setLinkType(LinkTypeEnum.INNER);
		request.setType(TypeBelongEnum.NOVEL);
		request.setItemId(1L);
		request.setSwiperId(1L);
		Swiper swiper = swiperService.addOrUpdateSwiper(request).getData();
		assertThat(swiper.getItemId()).isEqualTo(1L);
	}

	@Test
	public void addSwiperFailedWhenSwiperExist() {
		assertException(InvalidArgumentException.class, () -> {
			AddOrUpdateSwiperRequest request = new AddOrUpdateSwiperRequest();
			request.setLinkType(LinkTypeEnum.INNER);
			request.setUserId(adminUserId);
			request.setType(TypeBelongEnum.NOVEL);
			request.setItemId(1L);
			swiperService.addOrUpdateSwiper(request);
		});
	}

	@Test
	public void addSwiperFailedWhenItemNoExist() {
		assertException(InvalidArgumentException.class, () -> {
			AddOrUpdateSwiperRequest request = new AddOrUpdateSwiperRequest();
			request.setUserId(adminUserId);
			request.setType(TypeBelongEnum.NOVEL);
			request.setLinkType(LinkTypeEnum.INNER);
			request.setItemId(-1L);
			swiperService.addOrUpdateSwiper(request);
		});
	}

	@Test
	public void updateSwiperFailedWhenSwiperNoExist() {
		assertException(InvalidArgumentException.class, () -> {
			AddOrUpdateSwiperRequest request = new AddOrUpdateSwiperRequest();
			request.setUserId(adminUserId);
			request.setSwiperId(-1L);
			request.setType(TypeBelongEnum.NOVEL);
			request.setItemId(2L);
			swiperService.addOrUpdateSwiper(request);
		});
	}

	@Test
	public void updateSwiperFailedWhenItemNoExist() {
		assertException(InvalidArgumentException.class, () -> {
			AddOrUpdateSwiperRequest request = new AddOrUpdateSwiperRequest();
			request.setUserId(adminUserId);
			request.setSwiperId(1L);
			request.setLinkType(LinkTypeEnum.INNER);
			request.setType(TypeBelongEnum.NOVEL);
			request.setItemId(-1L);
			swiperService.addOrUpdateSwiper(request);
		});
	}
}
