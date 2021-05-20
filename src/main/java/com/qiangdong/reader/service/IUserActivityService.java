package com.qiangdong.reader.service;

import com.qiangdong.reader.annotation.Validation;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.entity.Comment;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserActivity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_activity.*;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.validate.user_activity.AddOrUpdateActivityValidator;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户动态表 服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-25
 */
public interface IUserActivityService extends IService<UserActivity> {

    @Transactional(rollbackFor = RuntimeException.class)
    @Validation(AddOrUpdateActivityValidator.class)
    Response<UserActivity> addOrUpdateActivity(AddOrUpdateActivityRequest request, User user);

    PageResponse<UserActivityDto> listActivity(BaseRequest request);

	PageResponse<UserActivityDto> listUserActivity(ListUserActivityRequest request);

	PageResponse<UserActivityDto> listRecommendActivity(BaseRequest request);

	Response<String> deleteActivity(DeleteActivityRequest request);

	@Transactional(rollbackFor = Exception.class)
	Response<Integer> agreeActivity(AgreeActivityRequest request);

	@Transactional(rollbackFor = Exception.class)
	Response<Integer> againstActivity(AgainstActivityRequest request);

	Response<Integer> cancelAgreeActivity(GetUserActivityRequest request, UserActivity userActivity);

	Response<Integer> cancelAgainstActivity(GetUserActivityRequest request, UserActivity userActivity);

	PageResponse<UserActivityDto> listActivityByManager(ListUserActivityRequest request);

	Response<UserActivityDto> getUserActivity(GetUserActivityRequest request);

    PageResponse<UserActivityDto> searchUserActivity(SearchUserActivityRequest request);

    @Transactional(rollbackFor = RuntimeException.class)
	Response<UserActivity> changeTopStatus(ChangeActivityTopStatusRequest request, UserActivity userActivity);

	Response<String> deleteActivityByManager(DeleteUserActivityRequest request);

	PageResponse<UserActivityDto> listFollowUserActivity(BaseRequest request);

	@Transactional(rollbackFor = RuntimeException.class)
	Response<UserActivity> shareUserActivity(ShareUserActivityRequest request);

    Response<UserActivityDto> getUserActivityByComment(GetUserActivityByCommentRequest request, Comment comment);
}
