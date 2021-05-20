package com.qiangdong.reader.dto.fate_board;

import com.qiangdong.reader.enums.user.UserSexEnum;
import java.util.Objects;

public class FateBoardMatchDto {
	private Long userId;
	private String interest;
	private UserSexEnum sex;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public UserSexEnum getSex() {
		return sex;
	}

	public void setSex(UserSexEnum sex) {
		this.sex = sex;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		FateBoardMatchDto matchDto = (FateBoardMatchDto) o;
		return Objects.equals(userId, matchDto.userId) &&
			Objects.equals(interest, matchDto.interest) &&
			sex == matchDto.sex;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, interest, sex);
	}
}
