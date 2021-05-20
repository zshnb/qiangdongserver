package com.qiangdong.reader.response.user;

import com.qiangdong.reader.dto.UserPreferTypeDto;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.entity.UserPreferType;
import java.util.List;

/**
 * @author Fyf
 */
public class ListUserPreferTypeResponse {
	List<Type> allType;
	List<UserPreferTypeDto> userPreferType;

	public List<Type> getAllType() {
		return allType;
	}

	public void setAllType(List<Type> allType) {
		this.allType = allType;
	}

	public List<UserPreferTypeDto> getUserPreferType() {
		return userPreferType;
	}

	public void setUserPreferType(List<UserPreferTypeDto> userPreferType) {
		this.userPreferType = userPreferType;
	}
}
