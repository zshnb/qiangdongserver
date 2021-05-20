package com.qiangdong.reader.bookstand;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.BookStand;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.bookstand.AddBookStandRequest;
import com.qiangdong.reader.serviceImpl.BookStandServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BookStandServiceAddBookStandTest extends BaseTest {
	@Autowired
	private BookStandServiceImpl bookStandService;

	@Test
	public void addBookStandSuccessful() {
		AddBookStandRequest request = new AddBookStandRequest();
		request.setAssociateId(2L);
		request.setAssociateType(WorksTypeEnum.NOVEL);
		BookStand bookStand = bookStandService.addBookStand(request).getData();
		assertThat(bookStand.getId()).isGreaterThan(0L);
	}

	@Test
	public void addBookStandFailedWhenBookStandExist() {
		AddBookStandRequest request = new AddBookStandRequest();
		request.setUserId(1L);
		request.setAssociateId(1L);
		request.setAssociateType(WorksTypeEnum.NOVEL);
		assertException(InvalidArgumentException.class, () -> bookStandService.addBookStand(request));
	}

	@Test
	public void addBookStandFailedWhenNovelNotExist() {
		AddBookStandRequest request = new AddBookStandRequest();
		request.setAssociateId(-1L);
		request.setAssociateType(WorksTypeEnum.NOVEL);
		assertException(InvalidArgumentException.class, () -> bookStandService.addBookStand(request));
	}

	@Test
	public void addBookStandFailedWhenComicNotExist() {
		AddBookStandRequest request = new AddBookStandRequest();
		request.setAssociateId(-1L);
		request.setAssociateType(WorksTypeEnum.COMIC);
		assertException(InvalidArgumentException.class, () -> bookStandService.addBookStand(request));
	}
}
