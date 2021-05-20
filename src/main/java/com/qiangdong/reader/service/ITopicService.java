package com.qiangdong.reader.service;

import com.qiangdong.reader.dto.TopicDto;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.entity.Topic;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.topic.AddOrUpdateTopicRequest;
import com.qiangdong.reader.request.topic.FollowOrUnFollowTopicRequest;
import com.qiangdong.reader.request.topic.GetTopicRequest;
import com.qiangdong.reader.request.topic.ListHeatUserActivityRequest;
import com.qiangdong.reader.request.topic.ListNewestUserActivityRequest;
import com.qiangdong.reader.request.topic.ListSimilarTopicRequest;
import com.qiangdong.reader.request.topic.SearchTopicRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.search.TopicForSearch;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 用户协议 服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-16
 */
public interface ITopicService extends IService<Topic> {

	@Transactional(rollbackFor = RuntimeException.class)
	Response<Topic> addOrUpdateTopic(@RequestBody AddOrUpdateTopicRequest request);

	Response<TopicDto> getTopic(GetTopicRequest request);

	PageResponse<Topic> listTopic(BaseRequest request);

	PageResponse<TopicForSearch> searchTopic(SearchTopicRequest request);

	PageResponse<Topic> listHotTopic(BaseRequest request);

	PageResponse<TopicDto> listNewestTopic(BaseRequest request);

	Response<String> followTopic(FollowOrUnFollowTopicRequest request, Topic topic);

	Response<String> unFollowTopic(FollowOrUnFollowTopicRequest request, Topic topic);

	PageResponse<TopicDto> listSimilarTopic(ListSimilarTopicRequest request);

	PageResponse<UserActivityDto> listHeatUserActivity(ListHeatUserActivityRequest request, Topic topic);

	PageResponse<UserActivityDto> listNewestUserActivity(ListNewestUserActivityRequest request, Topic topic);
}
