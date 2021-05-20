package com.qiangdong.reader.goods;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Goods;
import com.qiangdong.reader.enums.goods.GoodsTypeEnum;
import com.qiangdong.reader.request.goods.ListGoodsByTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.GoodsServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GoodsServiceListUserOwnGoodsTest extends BaseTest {
	@Autowired
	private GoodsServiceImpl goodsService;

	@Test
	public void listGoodsSuccessful() {
		ListGoodsByTypeRequest request = new ListGoodsByTypeRequest();
		request.setUserId(1L);
		request.setType(GoodsTypeEnum.FATE_BOARD);
		PageResponse<Goods> response = goodsService.listUserOwnGoods(request);
		assertThat(response.getList().size()).isEqualTo(1);
	}
}
