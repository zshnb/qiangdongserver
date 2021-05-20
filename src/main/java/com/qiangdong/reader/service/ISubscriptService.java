package com.qiangdong.reader.service;

import com.qiangdong.reader.dto.SubscribeDto;
import com.qiangdong.reader.entity.Subscribe;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.request.subscribe.GetWorksStatisticsRequest;
import com.qiangdong.reader.request.subscribe.ListWorksChapterSubscribeRequest;
import com.qiangdong.reader.request.subscribe.SubscribeChapterRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.subscribe.WorksStatisticsResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
public interface ISubscriptService extends IService<Subscribe> {

	@Transactional(rollbackFor = RuntimeException.class)
	Response<String> subscribeChapter(SubscribeChapterRequest request, User user);

	WorksStatisticsResponse getWorksStatistics(GetWorksStatisticsRequest request);

	PageResponse<SubscribeDto> listChapterSubscribe(ListWorksChapterSubscribeRequest request);
}
