package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.annotation.RequireAdmin;
import com.qiangdong.reader.entity.HelpFeedback;
import com.qiangdong.reader.dao.HelpFeedbackMapper;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.help_feedback.AddOrUpdateHelpFeedbackRequest;
import com.qiangdong.reader.request.help_feedback.AnswerHelpFeedbackRequest;
import com.qiangdong.reader.request.help_feedback.DeleteHelpFeedbackRequest;
import com.qiangdong.reader.request.help_feedback.GetHelpFeedbackRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IHelpFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 帮助与反馈 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-09
 */
@Service
public class HelpFeedbackServiceImpl extends ServiceImpl<HelpFeedbackMapper, HelpFeedback> implements
	IHelpFeedbackService {

	private final PageUtil pageUtil;

	public HelpFeedbackServiceImpl(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}

	@Override
	public PageResponse<HelpFeedback> listHelpFeedback(BaseRequest request) {
		QueryWrapper<HelpFeedback> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByAsc("create_at");
		IPage<HelpFeedback> listHelpFeedback = page(pageUtil.of(request), queryWrapper);
		return PageResponse.of(listHelpFeedback, request.getPageSize());
	}

	@Override
	public PageResponse<HelpFeedback> listHelpFeedbackByUser(BaseRequest request) {
		IPage<HelpFeedback> helpFeedbackIPage = page(pageUtil.of(request), new QueryWrapper<HelpFeedback>()
			.eq("user_id", request.getUserId())
			.orderByDesc("create_at"));
		return PageResponse.of(helpFeedbackIPage, request.getPageSize());
	}

	@Override
	public Response<HelpFeedback> getHelpFeedback(GetHelpFeedbackRequest request, HelpFeedback helpFeedback) {
		return Response.ok(helpFeedback);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<HelpFeedback> addOrUpdateHelpFeedback(AddOrUpdateHelpFeedbackRequest request) {
		HelpFeedback helpFeedback;
		if (request.getHelpFeedbackId() == 0L) {
			helpFeedback = new HelpFeedback();
			helpFeedback.setAnswer("");
			BeanUtils.copyProperties(request, helpFeedback);
			save(helpFeedback);
		} else {
			helpFeedback = getById(request.getHelpFeedbackId());
			AssertUtil.assertNotNull(helpFeedback, "帮助反馈不存在");
			BeanUtils.copyProperties(request, helpFeedback);
			updateById(helpFeedback);
		}
		return Response.ok(helpFeedback);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<String> deletedHelpFeedback(DeleteHelpFeedbackRequest request, HelpFeedback helpFeedback) {
		removeById(request.getHelpFeedbackId());
		return Response.ok();
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	@RequireAdmin
	public Response<String> answeredHelpFeedback(AnswerHelpFeedbackRequest request, HelpFeedback helpFeedback) {
		helpFeedback.setAnswer(request.getAnswer());
		updateById(helpFeedback);
		return Response.ok();
	}
}
