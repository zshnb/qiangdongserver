package com.qiangdong.reader.request.swiper;

import com.qiangdong.reader.request.BaseRequest;

public class DeleteSwiperRequest extends BaseRequest {
	private Long swiperId = 0L;

	public Long getSwiperId() {
		return swiperId;
	}

	public void setSwiperId(Long swiperId) {
		this.swiperId = swiperId;
	}
}
