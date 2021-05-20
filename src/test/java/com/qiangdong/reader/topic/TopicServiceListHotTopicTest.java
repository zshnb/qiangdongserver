package com.qiangdong.reader.topic;

import static org.mockito.ArgumentMatchers.any;

import cn.hutool.core.collection.CollectionUtil;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user_activity.ActivityData;
import com.qiangdong.reader.dto.user_activity.CreateActivity;
import com.qiangdong.reader.entity.Topic;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.user_activity.ActivityTypeEnum;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_activity.AddOrUpdateActivityRequest;
import com.qiangdong.reader.serviceImpl.TopicServiceImpl;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import com.qiangdong.reader.utils.UserActivityUtil;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

public class TopicServiceListHotTopicTest extends BaseTest {
	@Autowired
	private TopicServiceImpl topicService;

	@Autowired
	private UserActivityServiceImpl userActivityService;

	@SpyBean
	private UserActivityUtil userActivityUtil;

	@Before
	public void beforeMock() {
		Mockito.doNothing().when(userActivityUtil).indexUserActivity(any(), any());
	}

	@Test
	public void listSuccessful() {
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
		userActivityService.addOrUpdateActivity(request, new User()).getData();

		List<Topic> topics = topicService.listHotTopic(new BaseRequest()).getList();
		assertThat(topics.size()).isEqualTo(1);
		assertThat(topics.get(0).getName()).isEqualTo("topic1");
	}
}
