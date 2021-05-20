package com.qiangdong.reader.service;

import com.qiangdong.reader.dto.BookStandDto;
import com.qiangdong.reader.entity.BookStand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.bookstand.AddBookStandRequest;
import com.qiangdong.reader.request.bookstand.DeleteBookStandRequest;
import com.qiangdong.reader.request.bookstand.ListBookStandByTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 书架记录 服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
public interface IBookStandService extends IService<BookStand> {

	@Transactional(rollbackFor = RuntimeException.class)
	Response<BookStand> addBookStand(AddBookStandRequest request);

	PageResponse<BookStandDto> listBookStandByType(ListBookStandByTypeRequest request);

	Response<String> deleteBookStand(DeleteBookStandRequest request, BookStand bookStand);
}
