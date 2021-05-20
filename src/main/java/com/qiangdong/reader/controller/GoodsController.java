package com.qiangdong.reader.controller;


import com.qiangdong.reader.entity.Goods;
import com.qiangdong.reader.request.goods.ListGoodsByTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.GoodsServiceImpl;
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
@RequestMapping("/goods")
public class GoodsController {
	private GoodsServiceImpl goodsService;

	public GoodsController(GoodsServiceImpl goodsService) {
		this.goodsService = goodsService;
	}

	@PostMapping("/list-by-type")
	public PageResponse<Goods> listGoodsByType(@RequestBody ListGoodsByTypeRequest request) {
		return goodsService.listGoodsByType(request);
	}

	@PostMapping("/list-own")
	public PageResponse<Goods> listUserOwnGoods(@RequestBody ListGoodsByTypeRequest request) {
		return goodsService.listUserOwnGoods(request);
	}
}
