package com.qiangdong.reader.comic;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.comic.ComicDto;
import com.qiangdong.reader.enums.common.WorksUpdateStatusEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comic.ListComicByTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.ComicServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicServiceListComicByTypeTest extends BaseTest {
	@Autowired
	private ComicServiceImpl comicService;

	@Test
	public void listComicByTypeSuccess() {
		ListComicByTypeRequest request = new ListComicByTypeRequest();
		request.setTypeId(3L);
		PageResponse<ComicDto> response = comicService.listComicByType(request);
		assertThat(response.getList().size()).isEqualTo(1);

		ComicDto comicDto = response.getList().get(0);
		assertThat(comicDto.getComicId()).isEqualTo(1L);
		assertThat(comicDto.getName()).isEqualTo("comic1");
		assertThat(comicDto.getUpdateStatus()).isEqualTo(WorksUpdateStatusEnum.UPDATING);
	}
}
