package com.qiangdong.reader.service;

import com.qiangdong.reader.annotation.RequireAdmin;
import com.qiangdong.reader.dto.SwiperDto;
import com.qiangdong.reader.entity.Swiper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.swiper.AddOrUpdateSwiperRequest;
import com.qiangdong.reader.request.swiper.DeleteSwiperRequest;
import com.qiangdong.reader.request.swiper.ListSwiperRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 轮播图 服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-11
 */
public interface ISwiperService extends IService<Swiper> {

	Response<Swiper> addOrUpdateSwiper(AddOrUpdateSwiperRequest request);

	PageResponse<SwiperDto> listSwiperByManager(ListSwiperRequest request);

	PageResponse<Swiper> listSwiper(ListSwiperRequest request);

	@Transactional(rollbackFor = RuntimeException.class)
	@RequireAdmin
	Response<String> deleteSwiper(DeleteSwiperRequest request, Swiper swiper);
}
