package com.qiangdong.reader.controller;


import com.qiangdong.reader.entity.Swiper;
import com.qiangdong.reader.request.swiper.ListSwiperRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.SwiperServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 轮播图 前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-11
 */
@RestController
@RequestMapping("/swiper")
public class SwiperController {
	private final SwiperServiceImpl swiperService;

	public SwiperController(SwiperServiceImpl swiperService) {
		this.swiperService = swiperService;
	}

	@PostMapping("/list")
	public PageResponse<Swiper> listSwiper(@RequestBody ListSwiperRequest request) {
		return swiperService.listSwiper(request);
	}
}
