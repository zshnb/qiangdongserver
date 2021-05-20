package com.qiangdong.reader.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qiangdong.reader.enums.user.UserSexEnum;
import com.qiangdong.reader.request.BaseRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public class UpdateUserInfoRequest extends BaseRequest {
	@Size(min = 1, max = 8, message = "昵称长度在1-8之间")
	private String username = "";

	private String address = "";

	private UserSexEnum sex = UserSexEnum.NONE;

	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate birthday = LocalDate.now();

	private String avatar = "";

	@Size(max = 20, message = "个性签名不能超过20字")
	private String signature = "";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public UserSexEnum getSex() {
		return sex;
	}

	public void setSex(UserSexEnum sex) {
		this.sex = sex;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
}
