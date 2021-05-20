package com.qiangdong.reader.dto.fate_board;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qiangdong.reader.dto.goods.FateBoardGoods;
import com.qiangdong.reader.enums.fate_board.FateBoardStatusEnum;
import com.qiangdong.reader.enums.user.UserSexEnum;

public class FateBoardDto {
	@JsonSerialize(using = ToStringSerializer.class)
	private Long fateBoardId;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;
	private String username;
	private UserSexEnum sex;
	private Integer age;
	private String avatar;
	private String interest;
	private String content;
	private FateBoardStatusEnum status;
	private UserSexEnum matchSex;
	private Boolean violation;
	private FateBoardGoods goods;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long goodsId;

	public FateBoardGoods getGoods() {
		return goods;
	}

	public void setGoods(FateBoardGoods goods) {
		this.goods = goods;
	}

	public Long getFateBoardId() {
		return fateBoardId;
	}

	public void setFateBoardId(Long fateBoardId) {
		this.fateBoardId = fateBoardId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public FateBoardStatusEnum getStatus() {
		return status;
	}

	public void setStatus(FateBoardStatusEnum status) {
		this.status = status;
	}

	public UserSexEnum getMatchSex() {
		return matchSex;
	}

	public void setMatchSex(UserSexEnum matchSex) {
		this.matchSex = matchSex;
	}

	public Boolean getViolation() {
		return violation;
	}

	public void setViolation(Boolean violation) {
		this.violation = violation;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
}
