package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.dao.MessageMapper;
import com.qiangdong.reader.entity.Message;
import com.qiangdong.reader.entity.Notice;
import com.qiangdong.reader.dao.NoticeMapper;
import com.qiangdong.reader.enums.message.MessageReadStatusEnum;
import com.qiangdong.reader.enums.message.MessageTypeEnum;
import com.qiangdong.reader.enums.notice.NoticeTypeEnum;
import com.qiangdong.reader.request.notice.AddOrUpdateNoticeRequest;
import com.qiangdong.reader.request.notice.DeleteNoticeRequest;
import com.qiangdong.reader.request.notice.ListNoticeByTypeRequest;
import com.qiangdong.reader.request.notice.GetNoticeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 公告表 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-04
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

	private final MessageMapper messageMapper;
	private final PageUtil pageUtil;

	public NoticeServiceImpl(MessageMapper messageMapper, PageUtil pageUtil) {
		this.messageMapper = messageMapper;
		this.pageUtil = pageUtil;
	}

	@Override
	public PageResponse<Notice> listNoticeByType(ListNoticeByTypeRequest request) {
		QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("type", request.getType()).
			orderByAsc("create_at");
		IPage<Notice> notices = page(pageUtil.of(request), queryWrapper);
		return PageResponse.of(notices, request.getPageSize());
	}

	@Override
	public Response<Notice> getNotice(GetNoticeRequest request, Notice notice) {
		return Response.ok(notice);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<Notice> addOrUpdateNotice(AddOrUpdateNoticeRequest request) {
		Notice notice;
		if (request.getNoticeId() == 0L) {
			notice = new Notice();
			BeanUtils.copyProperties(request, notice);
			save(notice);
			if (request.getType().equals(NoticeTypeEnum.NOTICE_APP)) {
				Message message = new Message();
				message.setReadStatus(MessageReadStatusEnum.UNREAD);
				message.setAssociateId(notice.getId());
				message.setType(MessageTypeEnum.SYSTEM_MESSAGE);
				message.setUserId(0L);
				messageMapper.insert(message);
			}
		} else {
			notice = getById(request.getNoticeId());
			AssertUtil.assertNotNull(notice, "找不到该公告");
			BeanUtils.copyProperties(request, notice);
			updateById(notice);
		}
		return Response.ok(notice);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<String> deleteNotice(DeleteNoticeRequest request, Notice notice) {
		removeById(notice.getId());
		return Response.ok();
	}
}
