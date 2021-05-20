package com.qiangdong.reader.controller;


import com.qiangdong.reader.entity.HelpFeedback;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.help_feedback.AddOrUpdateHelpFeedbackRequest;
import com.qiangdong.reader.request.help_feedback.DeleteHelpFeedbackRequest;
import com.qiangdong.reader.request.help_feedback.GetHelpFeedbackRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.HelpFeedbackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 帮助与反馈 前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-09
 */
@RestController
@RequestMapping("/help-feedback")
public class HelpFeedbackController {
	@Autowired
	private HelpFeedbackServiceImpl helpFeedbackService;

	@PostMapping("/list")
	public PageResponse<HelpFeedback> listHelpFeedback(@RequestBody BaseRequest request) {
		return helpFeedbackService.listHelpFeedback(request);
	}

	@PostMapping("/add-update")
	public Response<HelpFeedback> addOrUpdateHelpFeedback(@RequestBody AddOrUpdateHelpFeedbackRequest request) {
		return helpFeedbackService.addOrUpdateHelpFeedback(request);
	}

	@PostMapping("/{helpFeedbackId}")
	public Response<HelpFeedback> getHelpFeedback(@RequestBody GetHelpFeedbackRequest request) {
		return helpFeedbackService.getHelpFeedback(request, new HelpFeedback());
	}

	@DeleteMapping("/{helpFeedbackId}")
	public Response<String> deleteHelpFeedback(@RequestBody DeleteHelpFeedbackRequest request) {
		return helpFeedbackService.deletedHelpFeedback(request, new HelpFeedback());
	}
}
