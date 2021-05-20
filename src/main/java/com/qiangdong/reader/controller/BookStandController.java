package com.qiangdong.reader.controller;


import com.qiangdong.reader.dto.BookStandDto;
import com.qiangdong.reader.entity.BookStand;
import com.qiangdong.reader.request.bookstand.AddBookStandRequest;
import com.qiangdong.reader.request.bookstand.DeleteBookStandRequest;
import com.qiangdong.reader.request.bookstand.ListBookStandByTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.BookStandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 书架记录 前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@RestController
@RequestMapping("/book-stand")
public class BookStandController {
	@Autowired
	private BookStandServiceImpl bookStandService;

	@PostMapping("/add")
	public Response<BookStand> addBookStand(@RequestBody AddBookStandRequest request) {
		return bookStandService.addBookStand(request);
	}

	@PostMapping("/list")
	public PageResponse<BookStandDto> listBookStandByType(@RequestBody ListBookStandByTypeRequest request) {
		return bookStandService.listBookStandByType(request);
	}

	@DeleteMapping("/{bookStandId}")
	public Response<String> deleteBookStand(@RequestBody DeleteBookStandRequest request) {
		return bookStandService.deleteBookStand(request, new BookStand());
	}
}
