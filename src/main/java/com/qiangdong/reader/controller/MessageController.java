package com.qiangdong.reader.controller;


import com.qiangdong.reader.dto.MessageDto;
import com.qiangdong.reader.entity.Comment;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.comment.MentionRequest;
import com.qiangdong.reader.request.message.ListMessageByTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.message.GetMessageSummaryResponse;
import com.qiangdong.reader.serviceImpl.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 消息 前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-31
 */
@RestController
@RequestMapping("/message")
public class MessageController {
	@Autowired
	private MessageServiceImpl messageService;

	@PostMapping("/list")
	public PageResponse<MessageDto> listMessageByType(@RequestBody ListMessageByTypeRequest request) {
		return messageService.listMessageByType(request);
	}

	@PostMapping("/summary")
	public GetMessageSummaryResponse getMessageSummary(@RequestBody BaseRequest request) {
		return messageService.getMessageSummary(request);
	}

	@PostMapping("/mention")
	public Response<String> mention(@RequestBody MentionRequest request) {
		return messageService.mention(request);
	}
}
