package com.qiangdong.reader.bookstand;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.BookStandDto;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.bookstand.ListBookStandByTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.BookStandServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BookStandServiceListBookStandByTypeTest extends BaseTest {
	@Autowired
	private BookStandServiceImpl bookStandService;

	@Test
	public void listSuccessful() {
		ListBookStandByTypeRequest request = new ListBookStandByTypeRequest();
		request.setType(WorksTypeEnum.NOVEL);
		request.setUserId(1L);
		PageResponse<BookStandDto> response = bookStandService.listBookStandByType(request);
		assertThat(response.getList().size()).isEqualTo(1);
		BookStandDto bookStandDto = response.getList().get(0);
		assertThat(bookStandDto.getAuthorName()).isEqualTo("author 1");
		assertThat(bookStandDto.getName()).isEqualTo("novel1");
	}
}
