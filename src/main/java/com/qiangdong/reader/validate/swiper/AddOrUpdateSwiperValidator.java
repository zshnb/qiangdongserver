package com.qiangdong.reader.validate.swiper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.dao.SwiperMapper;
import com.qiangdong.reader.entity.Swiper;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.swiper.AddOrUpdateSwiperRequest;
import com.qiangdong.reader.validate.RequestValidator;
import org.springframework.stereotype.Component;

@Component
public class AddOrUpdateSwiperValidator extends RequestValidator<AddOrUpdateSwiperRequest> {
	private final SwiperMapper swiperMapper;

	public AddOrUpdateSwiperValidator(SwiperMapper swiperMapper) {
		this.swiperMapper = swiperMapper;
	}

	@Override
	public void validate(AddOrUpdateSwiperRequest request) {
		QueryWrapper<Swiper> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("type", request.getType().code())
			.eq("item_id", request.getItemId());
		if (request.getSwiperId() == 0L) {
			Swiper swiper = swiperMapper.selectOne(queryWrapper);
			if (swiper != null) {
				throw new InvalidArgumentException("轮播图已存在");
			}
		} else {
			Swiper swiper = swiperMapper.selectById(request.getSwiperId());
			if (swiper == null) {
				throw new InvalidArgumentException("轮播图不存在");
			}
			if (!swiper.getItemId().equals(request.getItemId())) {
				Swiper mayExist = swiperMapper.selectOne(queryWrapper);
				if (mayExist != null) {
					throw new InvalidArgumentException("轮播图已存在");
				}
			}
		}
	}
}
