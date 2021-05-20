package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.annotation.RequireEditor;
import com.qiangdong.reader.dao.ComicMapper;
import com.qiangdong.reader.dao.NovelMapper;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.dto.FaqDto;
import com.qiangdong.reader.entity.*;
import com.qiangdong.reader.dao.WorksFaqMapper;
import com.qiangdong.reader.enums.common.CommonReadStatusEnum;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.enums.faq.FaqTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.faq.AddOrUpdateFaqRequest;
import com.qiangdong.reader.request.faq.DeleteFaqRequest;
import com.qiangdong.reader.request.faq.*;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IWorksFaqService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-23
 */
@Service
public class WorksFaqServiceImpl extends ServiceImpl<WorksFaqMapper, WorksFaq> implements IWorksFaqService {

	private final NovelMapper novelMapper;
	private final ComicMapper comicMapper;
	private final UserMapper userMapper;
	private final WorksFaqMapper faqMapper;
	private final PageUtil pageUtil;

	public WorksFaqServiceImpl(NovelMapper novelMapper, ComicMapper comicMapper,
	                           UserMapper userMapper, WorksFaqMapper faqMapper,
	                           PageUtil pageUtil) {
		this.novelMapper = novelMapper;
		this.comicMapper = comicMapper;
		this.userMapper = userMapper;
		this.faqMapper = faqMapper;
		this.pageUtil = pageUtil;
	}

	@Transactional(rollbackFor = RuntimeException.class)
	@Override
	public Response<WorksFaq> addOrUpdateFaq(AddOrUpdateFaqRequest request) {
		WorksFaq faq = null;
		if (request.getFaqId() == 0) {
			if (WorksTypeEnum.COMIC.equals(request.getWorksType())) {
				Comic comic = comicMapper.selectById(request.getWorksId());
				if (comic == null) {
					throw new InvalidArgumentException("漫画不存在");
				}
			} else {
				Novel novel = novelMapper.selectById(request.getWorksId());
				if (novel == null) {
					throw new InvalidArgumentException("小说不存在");
				}
			}
			User user = userMapper.selectById(request.getUserId());
			if (user == null) {
				throw new InvalidArgumentException("用户不存在");
			}
			faq = new WorksFaq();
			BeanUtils.copyProperties(request, faq);
			save(faq);
		} else {
			faq = getById(request.getFaqId());
			if (faq == null) {
				throw new InvalidArgumentException("咨询不存在");
			}
			BeanUtils.copyProperties(request, faq);
			update(new UpdateWrapper<WorksFaq>()
				.set(!request.getReadStatus().equals(CommonReadStatusEnum.NONE),
					"read_status", request.getReadStatus())
				.set(!request.getFaqType().equals(FaqTypeEnum.NONE),
					"faq_type", request.getFaqType())
				.set(!request.getQuestion().isEmpty(), "question", request.getQuestion())
				.set(!request.getAnswer().isEmpty(), "answer", request.getAnswer())
				.eq("id", faq.getId()));
		}
		return Response.ok(faq);
	}

	@Transactional(rollbackFor = RuntimeException.class)
	@Override
	public Response<String> deleteFaq(DeleteFaqRequest request) {
		WorksFaq faq = getById(request.getFaqId());
		if (faq == null) {
			throw new InvalidArgumentException("咨询不存在");
		}
		removeById(request.getFaqId());
		return Response.ok();
	}

	@Override
	public PageResponse<FaqDto> listFaq(ListFaqRequest request) {
		User user = userMapper.selectById(request.getUserId());
		if (user == null) {
			throw new InvalidArgumentException("用户不存在");
		}
		IPage<FaqDto> data =
				faqMapper.findFaqByUserIdAndCreateAt(pageUtil.of(request), user.getId(), request.getCreateAt());
		return PageResponse.of(data, request.getPageSize());
	}

	@Override
	public Response<FaqDto> getFaq(GetFaqRequest request) {
		WorksFaq faq = getById(request.getFaqId());
		if (faq == null) {
			throw new InvalidArgumentException("咨询不存在");
		}
		FaqDto data = new FaqDto();
		data.setFaqId(faq.getId().toString());
		BeanUtils.copyProperties(faq, data);
		if (WorksTypeEnum.COMIC.equals(faq.getWorksType())) {
			Comic comic = comicMapper.selectById(faq.getWorksId());
			data.setWorksName(comic.getName());
		} else {
			Novel novel = novelMapper.selectById(faq.getWorksId());
			data.setWorksName(novel.getName());
		}
		return Response.ok(data);
	}

	@Override
	public Response<Integer> getUnReadFaqCount(BaseRequest request) {
		Integer count = faqMapper.findFaqUnReadCountByUserId(request.getUserId());
		return Response.ok(count);
	}

	@Override
	public PageResponse<FaqDto> getSystemFaq(BaseRequest request) {
		IPage<FaqDto> data =
			faqMapper.findSystemFaqByUserId(pageUtil.of(request), request.getUserId());
		return PageResponse.of(data, request.getPageSize());
	}

	@Transactional(rollbackFor = RuntimeException.class)
	@Override
	public Response<String> clearUserFaq(BaseRequest request) {
		QueryWrapper queryWrapper = new QueryWrapper<WorksFaq>()
			.eq("user_id", request.getUserId());
		remove(queryWrapper);
		return Response.ok();
	}

	@Transactional(rollbackFor = RuntimeException.class)
	@Override
	public Response<String> readUserAllFaq(BaseRequest request) {
		UpdateWrapper updateWrapper = new UpdateWrapper<WorksFaq>()
			.set("read_status", CommonReadStatusEnum.READ.code())
			.eq("user_id", request.getUserId());
		update(updateWrapper);
		return Response.ok();
	}

	/**
	 * 编辑获取消息通知
	 */
	@RequireEditor
	@Override
	public PageResponse<FaqDto> listFaqByType(ListFaqManageRequest request, Type type) {
		IPage<FaqDto> faqDtoIPage = null;
		switch (type.getBelong()) {
			case NOVEL:
				faqDtoIPage =
					faqMapper.findFaqyByTypeAndNovel(pageUtil.of(request), type.getId());
				break;
			case COMIC:
				faqDtoIPage = faqMapper.findFaqyByTypeAndComic(pageUtil.of(request), type.getId());
				break;
		}
		faqDtoIPage.getRecords().stream().forEach(faq -> {

			System.out.println(faq.getAnswer());
			if (faq.getAnswer() != null && !faq.getAnswer().isEmpty()) {
				faq.setReply(true);
			} else {
				faq.setReply(false);
			}
		});
		return PageResponse.of(faqDtoIPage, request.getPageSize());
	}

	/**
	 * 编辑回复信息
	 */
	@RequireEditor
	@Transactional(rollbackFor = RuntimeException.class)
	@Override
	public Response<WorksFaq> replyFaqQuestion(ReplyFaqQuestionRequest request, WorksFaq faq) {
		faq.setAnswer(request.getAnswer());
		updateById(faq);
		return Response.ok(faq);
	}
}
