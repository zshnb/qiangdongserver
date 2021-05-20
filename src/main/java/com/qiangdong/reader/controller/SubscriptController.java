package com.qiangdong.reader.controller;


import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.request.subscribe.SubscribeChapterRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.SubscribeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@RestController
@RequestMapping("/subscript")
public class SubscriptController {
	@Autowired
	private SubscribeServiceImpl subscribeService;

	@PostMapping("/chapter")
	public Response<String> subscribeChapter(@RequestBody SubscribeChapterRequest request) {
		return subscribeService.subscribeChapter(request, new User());
	}
}
