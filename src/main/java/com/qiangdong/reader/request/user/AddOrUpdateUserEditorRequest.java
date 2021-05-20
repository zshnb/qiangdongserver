package com.qiangdong.reader.request.user;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.qiangdong.reader.dto.user.SecrecyConfig;
import com.qiangdong.reader.enums.user.UserChatStatusEnum;
import com.qiangdong.reader.enums.user.UserRoleEnum;
import com.qiangdong.reader.enums.user.UserSexEnum;
import com.qiangdong.reader.enums.user.UserSignStatusEnum;
import com.qiangdong.reader.request.BaseRequest;
import java.time.LocalDateTime;

public class AddOrUpdateUserEditorRequest extends BaseRequest {

	private Long editorId = 0L;
	private String username = "";
	private String nickname = "";
	private String avatar = "";
	private String mobile = "";
	private String password = "";
	private String introduction = "";
	private Integer coin = 0;
	private Integer recommendTicket = 0;
	private Integer wallTicket = 0;
	private UserRoleEnum role = UserRoleEnum.NONE;
	private String qqAccount = "";
	private String email = "";
	private String authName = "";
	private String idCard = "";
	private UserSignStatusEnum userSignStatus = UserSignStatusEnum.UNSIGNED;
	private String accessToken = "";
	private UserSexEnum sex = UserSexEnum.NONE;
	private Long levelId = 0L;
	private String levelName = "";
	private Integer age = 0;
	private String address = "";
	private String signature = "";
	private Boolean isFollow = false;
	private Long typeId = 0L;
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime birthday = LocalDateTime.now();
	private UserChatStatusEnum chatStatus;

	public Long getEditorId() {
		return editorId;
	}

	public void setEditorId(Long editorId) {
		this.editorId = editorId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Integer getCoin() {
		return coin;
	}

	public void setCoin(Integer coin) {
		this.coin = coin;
	}

	public Integer getRecommendTicket() {
		return recommendTicket;
	}

	public void setRecommendTicket(Integer recommendTicket) {
		this.recommendTicket = recommendTicket;
	}

	public Integer getWallTicket() {
		return wallTicket;
	}

	public void setWallTicket(Integer wallTicket) {
		this.wallTicket = wallTicket;
	}

	public UserRoleEnum getRole() {
		return role;
	}

	public void setRole(UserRoleEnum role) {
		this.role = role;
	}

	public String getQqAccount() {
		return qqAccount;
	}

	public void setQqAccount(String qqAccount) {
		this.qqAccount = qqAccount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public UserSignStatusEnum getUserSignStatus() {
		return userSignStatus;
	}

	public void setUserSignStatus(UserSignStatusEnum userSignStatus) {
		this.userSignStatus = userSignStatus;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public UserSexEnum getSex() {
		return sex;
	}

	public void setSex(UserSexEnum sex) {
		this.sex = sex;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Boolean getFollow() {
		return isFollow;
	}

	public void setFollow(Boolean follow) {
		isFollow = follow;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public LocalDateTime getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDateTime birthday) {
		this.birthday = birthday;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserChatStatusEnum getChatStatus() {
		return chatStatus;
	}

	public void setChatStatus(UserChatStatusEnum chatStatus) {
		this.chatStatus = chatStatus;
	}
}
