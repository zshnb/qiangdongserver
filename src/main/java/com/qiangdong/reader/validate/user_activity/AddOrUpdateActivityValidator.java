package com.qiangdong.reader.validate.user_activity;

import com.qiangdong.reader.enums.user_activity.ActivityTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_activity.AddOrUpdateActivityRequest;
import com.qiangdong.reader.validate.RequestValidator;
import org.springframework.stereotype.Component;

@Component
public class AddOrUpdateActivityValidator extends RequestValidator<AddOrUpdateActivityRequest> {

	@Override
	public void validate(AddOrUpdateActivityRequest request) {
		if (request.getActivityData().getCreateActivity().getContent().length() > 500) {
			throw new InvalidArgumentException("动态内容不能超过500字");
		}
	}
}
