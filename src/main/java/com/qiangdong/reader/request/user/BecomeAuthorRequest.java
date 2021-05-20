package com.qiangdong.reader.request.user;

import com.qiangdong.reader.enums.user.UserCreatorIdentityEnum;
import com.qiangdong.reader.enums.user.UserSexEnum;
import com.qiangdong.reader.request.BaseRequest;

public class BecomeAuthorRequest extends BaseRequest {
	private String nickname = "";
	private UserSexEnum sex = UserSexEnum.NONE;
	private Integer age = 0;
	private String qq = "";
	private String email = "";
	private String address = "";
	private UserCreatorIdentityEnum creatorIdentity = UserCreatorIdentityEnum.NONE;

	public UserCreatorIdentityEnum getCreatorIdentity() {
		return creatorIdentity;
	}

	public void setCreatorIdentity(UserCreatorIdentityEnum creatorIdentity) {
		this.creatorIdentity = creatorIdentity;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public UserSexEnum getSex() {
		return sex;
	}

	public void setSex(UserSexEnum sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
