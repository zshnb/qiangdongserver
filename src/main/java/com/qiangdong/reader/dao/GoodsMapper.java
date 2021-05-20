package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiangdong.reader.enums.goods.GoodsTypeEnum;
import org.apache.ibatis.annotations.Param;
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
public interface GoodsMapper extends BaseMapper<Goods> {
	IPage<Goods> findUserOwnGoods(Page<?> page,
	                              @Param("type") GoodsTypeEnum type,
	                              @Param("userId") Long userId);

	IPage<Goods> findByType(Page<?> page, @Param("type") GoodsTypeEnum type);

	Goods findByIdAndTypeAndUserId(@Param("goodsId") Long goodsId,
	                               @Param("type") GoodsTypeEnum type,
	                               @Param("userId") Long userId);
}
