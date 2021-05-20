package com.qiangdong.reader.service;

import com.qiangdong.reader.entity.Goods;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.user_goods.BuyUserGoodsRequest;
import com.qiangdong.reader.response.Response;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-15
 */
public interface IUserGoodsService extends IService<UserGoods> {
    Response<UserGoods> buyUserGoods(BuyUserGoodsRequest request, Goods goods, User user);
}
