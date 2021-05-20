package com.qiangdong.reader.service;

import com.qiangdong.reader.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.goods.ListGoodsByTypeRequest;
import com.qiangdong.reader.response.PageResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-15
 */
public interface IGoodsService extends IService<Goods> {

	PageResponse<Goods> listGoodsByType(ListGoodsByTypeRequest request);

	PageResponse<Goods> listUserOwnGoods(ListGoodsByTypeRequest request);
}
