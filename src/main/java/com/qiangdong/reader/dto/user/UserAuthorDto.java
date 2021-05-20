package com.qiangdong.reader.dto.user;


import com.qiangdong.reader.enums.user.UserCreatorIdentityEnum;
import com.qiangdong.reader.enums.user.UserSexEnum;

public class UserAuthorDto {

	private Long id = 0L;
	private String authName = "";
	private String nickname ="";
	private String avatar = "";
	private String levelName = "";
	private String mobile = "";
	private UserSexEnum sex = UserSexEnum.NONE;
	private UserCreatorIdentityEnum identity = UserCreatorIdentityEnum.NONE;
	private Integer checkNovel = 0;
	private Integer checkComic = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public UserSexEnum getSex() {
		return sex;
	}

	public void setSex(UserSexEnum sex) {
		this.sex = sex;
	}

	public UserCreatorIdentityEnum getIdentity() {
		return identity;
	}

	public void setIdentity(UserCreatorIdentityEnum identity) {
		this.identity = identity;
	}

	public Integer getCheckNovel() {
		return checkNovel;
	}

	public void setCheckNovel(Integer checkNovel) {
		this.checkNovel = checkNovel;
	}

	public Integer getCheckComic() {
		return checkComic;
	}

	public void setCheckComic(Integer checkComic) {
		this.checkComic = checkComic;
	}
}
