package com.qiangdong.reader.request.fate_board;

import com.qiangdong.reader.enums.fate_board.FateBoardStatusEnum;
import com.qiangdong.reader.enums.user.UserSexEnum;
import com.qiangdong.reader.request.BaseRequest;

public class AddOrUpdateFateBoardRequest extends BaseRequest {
	private Long fateBoardId = 0L;
	private FateBoardStatusEnum status = FateBoardStatusEnum.DOWN;
	private String content = "";
	private Long goodsId = 0L;
	private UserSexEnum matchSex = UserSexEnum.NONE;
	private String interest = "";

	public Long getFateBoardId() {
		return fateBoardId;
	}

	public void setFateBoardId(Long fateBoardId) {
		this.fateBoardId = fateBoardId;
	}

	public FateBoardStatusEnum getStatus() {
		return status;
	}

	public void setStatus(FateBoardStatusEnum status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public UserSexEnum getMatchSex() {
		return matchSex;
	}

	public void setMatchSex(UserSexEnum matchSex) {
		this.matchSex = matchSex;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}
}
