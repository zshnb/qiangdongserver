package com.qiangdong.reader.dao;

import com.qiangdong.reader.entity.UserGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-15
 */
@Repository
public interface UserGoodsMapper extends BaseMapper<UserGoods> {
    void saveAll(List<UserGoods> userGoods);
}
