package com.qiangdong.reader.serviceImpl;

import com.qiangdong.reader.dao.UserConsumptionMapper;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.entity.Goods;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserConsumption;
import com.qiangdong.reader.entity.UserGoods;
import com.qiangdong.reader.dao.UserGoodsMapper;
import com.qiangdong.reader.enums.user_consumption.ConsumptionTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_goods.BuyUserGoodsRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IUserGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-15
 */
@Service
public class UserGoodsServiceImpl extends ServiceImpl<UserGoodsMapper, UserGoods> implements IUserGoodsService {
    private final UserConsumptionMapper userConsumptionMapper;
    private final UserMapper userMapper;

    public UserGoodsServiceImpl(UserConsumptionMapper userConsumptionMapper, UserMapper userMapper) {
        this.userConsumptionMapper = userConsumptionMapper;
        this.userMapper = userMapper;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Response<UserGoods> buyUserGoods(BuyUserGoodsRequest request, Goods goods, User user) {
        if (goods.getCoin() > user.getCoin()) {
            throw new InvalidArgumentException("余额不足");
        }
        UserGoods userGoods = new UserGoods();
        userGoods.setUserId(request.getUserId());
        userGoods.setGoodsId(request.getGoodsId());
        save(userGoods);

        UserConsumption userConsumption = new UserConsumption();
        userConsumption.setAssociateId(goods.getId());
        userConsumption.setUserId(request.getUserId());
        userConsumption.setCount(goods.getCoin());
        userConsumption.setUserId(request.getUserId());
        userConsumption.setType(ConsumptionTypeEnum.WALL_COIN);
        userConsumption.setDescription(request.getType().description());
        userConsumptionMapper.insert(userConsumption);

        user.setCoin(user.getCoin() - goods.getCoin());
        userMapper.updateById(user);
        return Response.ok(userGoods);
    }
}
