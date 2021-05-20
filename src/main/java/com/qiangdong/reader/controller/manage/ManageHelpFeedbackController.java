package com.qiangdong.reader.controller.manage;

import com.qiangdong.reader.entity.HelpFeedback;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.help_feedback.AddOrUpdateHelpFeedbackRequest;
import com.qiangdong.reader.request.help_feedback.AnswerHelpFeedbackRequest;
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

@RestController
@RequestMapping("/manage/help-feedback")
public class ManageHelpFeedbackController {

	@Autowired
	private HelpFeedbackServiceImpl helpFeedbackService;

	@PostMapping("/list-help-feedback")
	public PageResponse<HelpFeedback> listHelpFeedback(@RequestBody BaseRequest request) {
		return helpFeedbackService.listHelpFeedback(request);
	}

	@PostMapping("/detail")
	public Response<HelpFeedback> getHelpFeedback(@RequestBody GetHelpFeedbackRequest request) {
		return helpFeedbackService.getHelpFeedback(request, new HelpFeedback());
	}

	@PostMapping("/add-update")
	public Response<HelpFeedback> addOrUpdateHelpFeedback(@RequestBody AddOrUpdateHelpFeedbackRequest request) {
		return helpFeedbackService.addOrUpdateHelpFeedback(request);
	}

	@DeleteMapping("/{helpFeedbackId}")
	public Response<String> deleteHelpFeedback(@RequestBody DeleteHelpFeedbackRequest request) {
		return helpFeedbackService.deletedHelpFeedback(request, new HelpFeedback());
	}

	@PostMapping("/answer")
	public Response<String> answerHelpFeedback(@RequestBody AnswerHelpFeedbackRequest request) {
		return helpFeedbackService.answeredHelpFeedback(request, new HelpFeedback());
	}
}
