package com.qiangdong.reader.controller.manage;


import com.qiangdong.reader.dto.SwiperDto;
import com.qiangdong.reader.entity.Swiper;
import com.qiangdong.reader.request.swiper.AddOrUpdateSwiperRequest;
import com.qiangdong.reader.request.swiper.DeleteSwiperRequest;
import com.qiangdong.reader.request.swiper.ListSwiperRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.SwiperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/manage/swiper")
public class ManageSwiperController {
	@Autowired
	private SwiperServiceImpl swiperService;

	@PostMapping("/add-update")
	public Response<Swiper> addOrUpdateSwiper(@RequestBody AddOrUpdateSwiperRequest request) {
		return swiperService.addOrUpdateSwiper(request);
	}

	@PostMapping("/list")
	public PageResponse<SwiperDto> list(@RequestBody ListSwiperRequest request) {
		return swiperService.listSwiperByManager(request);
	}

	@DeleteMapping("/{swiperId}")
	public Response<String> deleteSwiper(@RequestBody DeleteSwiperRequest request) {
		return swiperService.deleteSwiper(request, new Swiper());
	}
}
