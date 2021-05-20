package com.qiangdong.reader.dto.statistics;

import com.qiangdong.reader.enums.user.UserSexEnum;

public class ReadUserSexWithCount {
	private UserSexEnum sex;
	private Integer count;

	public UserSexEnum getSex() {
		return sex;
	}

	public void setSex(UserSexEnum sex) {
		this.sex = sex;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}