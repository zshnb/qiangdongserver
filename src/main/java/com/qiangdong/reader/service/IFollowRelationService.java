package com.qiangdong.reader.service;

import com.qiangdong.reader.dto.FollowRelationDto;
import com.qiangdong.reader.entity.FollowRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.follow_relation.FollowUserRequest;
import com.qiangdong.reader.request.follow_relation.ListFansUserRequest;
import com.qiangdong.reader.request.follow_relation.ListFollowUserRequest;
import com.qiangdong.reader.request.follow_relation.UnFollowUserRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 关注表 服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-25
 */
public interface IFollowRelationService extends IService<FollowRelation> {

	Response<FollowRelation> followUser(FollowUserRequest request);

	@Transactional
	Response<String> unFollowUser(UnFollowUserRequest request);

	PageResponse<FollowRelationDto> listFollowUser(ListFollowUserRequest request);

	PageResponse<FollowRelationDto> listFansUser(ListFansUserRequest request);
}
