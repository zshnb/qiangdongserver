package com.qiangdong.reader.service;

import com.qiangdong.reader.entity.HelpFeedback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.help_feedback.AddOrUpdateHelpFeedbackRequest;
import com.qiangdong.reader.request.help_feedback.AnswerHelpFeedbackRequest;
import com.qiangdong.reader.request.help_feedback.DeleteHelpFeedbackRequest;
import com.qiangdong.reader.request.help_feedback.GetHelpFeedbackRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 帮助与反馈 服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-09
 */
public interface IHelpFeedbackService extends IService<HelpFeedback> {

	PageResponse<HelpFeedback> listHelpFeedback(BaseRequest request);

    PageResponse<HelpFeedback> listHelpFeedbackByUser(BaseRequest request);

    Response<HelpFeedback> getHelpFeedback(GetHelpFeedbackRequest request, HelpFeedback helpFeedback);

	Response<HelpFeedback> addOrUpdateHelpFeedback(AddOrUpdateHelpFeedbackRequest request);

	Response<String> deletedHelpFeedback(DeleteHelpFeedbackRequest request,HelpFeedback helpFeedback);

	@Transactional(rollbackFor = RuntimeException.class)
	Response<String> answeredHelpFeedback(AnswerHelpFeedbackRequest request, HelpFeedback helpFeedback);
}
