package com.qiangdong.reader.goods;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Goods;
import com.qiangdong.reader.enums.goods.GoodsTypeEnum;
import com.qiangdong.reader.request.goods.ListGoodsByTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.GoodsServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GoodsServiceListGoodsByTypeTest extends BaseTest {
	@Autowired
	private GoodsServiceImpl goodsService;

	@Test
	public void listGoodsSuccessful() {
		ListGoodsByTypeRequest request = new ListGoodsByTypeRequest();
		request.setType(GoodsTypeEnum.FATE_BOARD);
		PageResponse<Goods> response = goodsService.listGoodsByType(request);
		assertThat(response.getList().size()).isEqualTo(2);
	}
}
