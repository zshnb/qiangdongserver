package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.entity.Goods;
import com.qiangdong.reader.dao.GoodsMapper;
import com.qiangdong.reader.request.goods.ListGoodsByTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.PageUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-15
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
	private GoodsMapper goodsMapper;
	private PageUtil pageUtil;

	public GoodsServiceImpl(GoodsMapper goodsMapper, PageUtil pageUtil) {
		this.goodsMapper = goodsMapper;
		this.pageUtil = pageUtil;
	}

	@Override
	public PageResponse<Goods> listGoodsByType(ListGoodsByTypeRequest request) {
		IPage<Goods> goods = goodsMapper.findByType(pageUtil.of(request), request.getType());
		return PageResponse.of(goods, request.getPageSize());
	}

	/**
	 * 用户已购买商品
	 * */
	@Override
	public PageResponse<Goods> listUserOwnGoods(ListGoodsByTypeRequest request) {
		IPage<Goods> goods = goodsMapper.findUserOwnGoods(pageUtil.of(request), request.getType(), request.getUserId());
		return PageResponse.of(goods, request.getPageSize());
	}
}
