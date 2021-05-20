package com.qiangdong.reader.validate.user;

import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.UpdateUserEmailRequest;
import com.qiangdong.reader.utils.EmailUtil;
import com.qiangdong.reader.validate.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateUserEmailValidator extends RequestValidator<UpdateUserEmailRequest> {

	@Autowired
	private EmailUtil emailUtil;

	@Override
	public void validate(UpdateUserEmailRequest request) {
		if (!emailUtil.isValidEmail(request.getEmail())) {
			throw new InvalidArgumentException("请确认邮箱格式");
		}
	}
}
