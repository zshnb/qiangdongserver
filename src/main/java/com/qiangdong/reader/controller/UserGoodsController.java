package com.qiangdong.reader.controller;


import com.qiangdong.reader.entity.Goods;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserGoods;
import com.qiangdong.reader.request.user_goods.BuyUserGoodsRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.UserGoodsServiceImpl;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-15
 */
@RestController
@RequestMapping("/user-goods")
public class UserGoodsController {

    private UserGoodsServiceImpl userGoodsService;

    public UserGoodsController(UserGoodsServiceImpl userGoodsService) {
        this.userGoodsService = userGoodsService;
    }

    @PostMapping("/buy")
    public Response<UserGoods> buyUserGoods(@RequestBody @Valid BuyUserGoodsRequest request){
        return userGoodsService.buyUserGoods(request, new Goods(), new User());
    }
}
