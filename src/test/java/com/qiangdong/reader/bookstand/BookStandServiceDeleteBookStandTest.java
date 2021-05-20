package com.qiangdong.reader.bookstand;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.BookStand;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.bookstand.DeleteBookStandRequest;
import com.qiangdong.reader.serviceImpl.BookStandServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BookStandServiceDeleteBookStandTest extends BaseTest {
	@Autowired
	private BookStandServiceImpl bookStandService;

	@Test
	public void deleteSuccessful() {
		DeleteBookStandRequest request = new DeleteBookStandRequest();
		request.setBookStandId(1L);
		bookStandService.deleteBookStand(request, new BookStand());
		BookStand bookStand = bookStandService.getById(1L);
		assertThat(bookStand).isNull();
	}

	@Test
	public void deleteFailedWhenBookStandNotExist() {
		DeleteBookStandRequest request = new DeleteBookStandRequest();
		request.setBookStandId(-1L);
		assertException(InvalidArgumentException.class, () -> bookStandService.deleteBookStand(request, new BookStand()));

	}
}
