package com.qiangdong.reader.user_activity;

import static org.mockito.ArgumentMatchers.any;

import cn.hutool.core.collection.CollectionUtil;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dao.TopicMapper;
import com.qiangdong.reader.dao.UserActivityMapper;
import com.qiangdong.reader.dto.user_activity.ActivityData;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.dto.user_activity.CreateActivity;
import com.qiangdong.reader.entity.Topic;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.enums.user_activity.ActivityTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_activity.AddOrUpdateActivityRequest;
import com.qiangdong.reader.request.user_activity.GetUserActivityRequest;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import com.qiangdong.reader.utils.UserActivityUtil;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

public class UserActivityServiceAddOrUpdateActivityTest extends BaseTest {
	@Autowired
	private UserActivityServiceImpl userActivityService;

	@Autowired
	private UserActivityMapper userActivityMapper;

	@Autowired
	private TopicMapper topicMapper;

	@SpyBean
	private UserActivityUtil userActivityUtil;

	@Before
	public void beforeMock() {
		Mockito.doNothing().when(userActivityUtil).indexUserActivity(any(), any());
	}

	@Test
	public void addActivitySuccessful() {
		AddOrUpdateActivityRequest request = new AddOrUpdateActivityRequest();
		request.setUserId(1L);
		request.setType(ActivityTypeEnum.PUBLISH_ACTIVITY);
		ActivityData data = new ActivityData();
		CreateActivity createActivity = new CreateActivity();
		createActivity.setContent("content");
		data.setCreateActivity(createActivity);
		request.setActivityData(data);
		List<Long> topicIds = CollectionUtil.newArrayList(1L);
		request.setTopicIds(topicIds);
		UserActivity userActivity = userActivityService.addOrUpdateActivity(request, new User()).getData();
		assertThat(userActivity.getId()).isGreaterThan(0L);
		assertThat(userActivity.getActivityData().getCreateActivity().getContent()).isEqualTo("content");

		Topic topic = topicMapper.selectById(1L);
		assertThat(topic.getReferenceCount()).isEqualTo(1);
	}

	@Test
	public void updateActivitySuccessful() {
		AddOrUpdateActivityRequest request = new AddOrUpdateActivityRequest();
		request.setUserId(1L);
		request.setActivityId(1L);
		request.setType(ActivityTypeEnum.PUBLISH_ACTIVITY);
		ActivityData data = new ActivityData();
		CreateActivity createActivity = new CreateActivity();
		createActivity.setContent("new content");
		data.setCreateActivity(createActivity);
		request.setActivityData(data);
		userActivityService.addOrUpdateActivity(request, new User());
		UserActivity userActivity = userActivityMapper.findById(1L);
		assertThat(userActivity.getActivityData().getCreateActivity().getContent()).isEqualTo("new content");
	}

	@Test
	public void updateActivityTopics() {
		AddOrUpdateActivityRequest request = new AddOrUpdateActivityRequest();
		request.setUserId(1L);
		request.setActivityId(1L);
		request.setType(ActivityTypeEnum.PUBLISH_ACTIVITY);
		List<Long> topicIds = CollectionUtil.newArrayList(1L, 2L, 3L);
		request.setTopicIds(topicIds);
		userActivityService.addOrUpdateActivity(request, new User());

		Topic topic = topicMapper.selectById(3L);
		assertThat(topic.getReferenceCount()).isEqualTo(1);

		topicIds = CollectionUtil.newArrayList(2L);
		request.setTopicIds(topicIds);
		userActivityService.addOrUpdateActivity(request, new User());

		topic = topicMapper.selectById(3L);
		assertThat(topic.getReferenceCount()).isEqualTo(0);

		topicIds = CollectionUtil.newArrayList(2L, 3L);
		request.setTopicIds(topicIds);
		userActivityService.addOrUpdateActivity(request, new User());

		GetUserActivityRequest getUserActivityRequest = new GetUserActivityRequest();
		getUserActivityRequest.setUserActivityId(1L);
		UserActivityDto activityDto = userActivityService.getUserActivity(getUserActivityRequest).getData();
		assertThat(activityDto.getTopics().size()).isEqualTo(2);
	}

	@Test
	public void updateActivityFailedWhenActivityNotExist() {
		AddOrUpdateActivityRequest request = new AddOrUpdateActivityRequest();
		request.setUserId(1L);
		request.setActivityId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			userActivityService.addOrUpdateActivity(request, new User());
		});
	}
}
