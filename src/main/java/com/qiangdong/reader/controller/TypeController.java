package com.qiangdong.reader.controller;

import com.qiangdong.reader.dto.TypeDto;
import com.qiangdong.reader.request.type.ListTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.TypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/type")
public class TypeController {

	@Autowired
	private TypeServiceImpl typeService;

	@PostMapping("/list")
	public PageResponse<TypeDto> listType(@RequestBody ListTypeRequest request) {
		return typeService.listTypeByBelong(request);
	}

}
