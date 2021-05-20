package com.qiangdong.reader.service;

import com.qiangdong.reader.dto.MessageDto;
import com.qiangdong.reader.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.comment.MentionRequest;
import com.qiangdong.reader.request.message.ListMessageByTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.message.GetMessageSummaryResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 消息 服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-31
 */
public interface IMessageService extends IService<Message> {

	PageResponse<MessageDto> listMessageByType(ListMessageByTypeRequest request);

	GetMessageSummaryResponse getMessageSummary(BaseRequest request);

	@Transactional(rollbackFor = RuntimeException.class)
	Response<String> mention(MentionRequest request);
}
