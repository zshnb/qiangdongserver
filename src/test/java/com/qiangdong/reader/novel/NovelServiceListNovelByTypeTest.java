package com.qiangdong.reader.novel;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.novel.NovelDto;
import com.qiangdong.reader.enums.common.WorksUpdateStatusEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.novel.ListNovelByTypeRequest;
import com.qiangdong.reader.request.novel.ListNovelByTypeRequest.Filter;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelServiceListNovelByTypeTest extends BaseTest {
	@Autowired
	private NovelServiceImpl novelService;

	@Test
	public void listNovelByTypeSuccess() {
		ListNovelByTypeRequest request = new ListNovelByTypeRequest();
		request.setTypeId(3L);
		PageResponse<NovelDto> response = novelService.listNovelByType(request);
		Assert.assertEquals(1, response.getList().size());

		NovelDto novel = response.getList().get(0);
		Assert.assertEquals(1L, novel.getNovelId().longValue());
		Assert.assertEquals("novel1", novel.getName());
		Assert.assertEquals(WorksUpdateStatusEnum.UPDATING, novel.getUpdateStatus());
	}

	@Test
	public void listNovelByParentTypeSuccess() {
		ListNovelByTypeRequest request = new ListNovelByTypeRequest();
		request.setTypeId(1L);
		PageResponse<NovelDto> response = novelService.listNovelByType(request);
		Assert.assertEquals(1, response.getList().size());

		NovelDto novel = response.getList().get(0);
		Assert.assertEquals(1L, novel.getNovelId().longValue());
		Assert.assertEquals("novel1", novel.getName());
		Assert.assertEquals(WorksUpdateStatusEnum.UPDATING, novel.getUpdateStatus());
	}

	@Test
	public void listNovelWithFilterSuccessful() {
		ListNovelByTypeRequest request = new ListNovelByTypeRequest();
		Filter filter = new Filter();
		filter.setMinWordCount(0);
		filter.setMaxWordCount(200_000);
		filter.setUpdateStatus(WorksUpdateStatusEnum.UPDATING);
		request.setFilter(filter);
		PageResponse<NovelDto> response = novelService.listNovelByType(request);
		Assert.assertEquals(1, response.getList().size());
	}
}
