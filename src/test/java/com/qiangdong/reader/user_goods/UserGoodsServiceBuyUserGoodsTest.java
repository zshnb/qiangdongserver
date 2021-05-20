package com.qiangdong.reader.user_goods;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Goods;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserGoods;
import com.qiangdong.reader.enums.goods.GoodsTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_goods.BuyUserGoodsRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.UserGoodsServiceImpl;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserGoodsServiceBuyUserGoodsTest extends BaseTest {
    @Autowired
    private UserGoodsServiceImpl userGoodsService;

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void buyUserGoodsSuccessful() {
        BuyUserGoodsRequest request = new BuyUserGoodsRequest();
        request.setUserId(userId);
        request.setGoodsId(1L);
        request.setType(GoodsTypeEnum.FATE_BOARD);
        Response<UserGoods> userGoodsResponse = userGoodsService.buyUserGoods(request, new Goods(), new User());
        assertThat(userGoodsResponse.getData().getId()).isNotZero();
        User user = userService.getById(userId);
        assertThat(user.getCoin()).equals(19997);
    }

    @Test
    public void buyUserGoodsFailedWhenGoodsNoExist() {
        BuyUserGoodsRequest request = new BuyUserGoodsRequest();
        request.setUserId(userId);
        request.setGoodsId(-1L);
        request.setType(GoodsTypeEnum.FATE_BOARD);
        assertException(InvalidArgumentException.class, () -> {
            userGoodsService.buyUserGoods(request, new Goods(), new User());
        });
    }

    @Test
    public void buyUserGoodsFailedWhenCoinNotEnough() {
        BuyUserGoodsRequest request = new BuyUserGoodsRequest();
        request.setUserId(3L);
        request.setGoodsId(2L);
        request.setType(GoodsTypeEnum.FATE_BOARD);
        assertException(InvalidArgumentException.class, () -> {
            userGoodsService.buyUserGoods(request, new Goods(), new User());
        });
    }
}
