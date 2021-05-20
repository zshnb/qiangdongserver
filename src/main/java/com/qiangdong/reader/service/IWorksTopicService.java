package com.qiangdong.reader.service;

import com.qiangdong.reader.dto.WorksTopicDto;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.entity.WorksTopic;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.works_topic.*;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.works_topic.GetWorksTopicResponse;
import com.qiangdong.reader.response.works_topic.ListWorksTopicWorksResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-23
 */
public interface IWorksTopicService extends IService<WorksTopic> {
	Response<WorksTopic> addOrUpdateTopic(AddOrUpdateWorksTopicRequest request, Type type);

	PageResponse<WorksTopicDto> listTopic(ListWorksTopicRequest request);

	Response<String> deleteTopic(DeleteWorksTopicRequest request);

	GetWorksTopicResponse getTopic(GetTopicRequest request);

	PageResponse<WorksTopicDto> listWorksTopic(ListWorksTopicRequest request);

	ListWorksTopicWorksResponse listTopicWorks(ListTopicWorksRequest request, WorksTopic worksTopic);

	Response<String> addTopicWorks(AddTopicWorksRequest request, WorksTopic worksTopic);

}
