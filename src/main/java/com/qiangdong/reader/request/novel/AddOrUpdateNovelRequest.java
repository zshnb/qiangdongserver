package com.qiangdong.reader.request.novel;

import com.qiangdong.reader.dto.WorksTagDto;
import com.qiangdong.reader.enums.common.WorksAuthorizationStatusEnum;
import com.qiangdong.reader.enums.common.WorksContractStatusEnum;
import com.qiangdong.reader.enums.common.WorksShowStatusEnum;
import com.qiangdong.reader.enums.common.WorksUpdateStatusEnum;
import com.qiangdong.reader.request.BaseRequest;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public class AddOrUpdateNovelRequest extends BaseRequest {
	private Long novelId = 0L;
	/**
	 * 封面url
	 */
	private String cover = "";

	/**
	 * 作者
	 */
	private Long authorId = 0L;

	/**
	 * 字数
	 */
	private Integer wordCount = 0;

	/**
	 * 更新状态
	 */
	private WorksUpdateStatusEnum updateStatus = WorksUpdateStatusEnum.NONE;

	/**
	 * 签约状态
	 */
	private WorksContractStatusEnum contractStatus = WorksContractStatusEnum.NONE;

	/**
	 * 上架状态
	 */
	private WorksShowStatusEnum showStatus = WorksShowStatusEnum.NORMAL;

	/**
	 * 小说名字
	 */
	@Size(min = 1, max = 15, message = "名称在1-15字之间")
	private String name = "";

	/**
	 * 收藏数
	 */
	private Integer collectCount = 0;

	/**
	 * 推荐数
	 */
	private Integer recommendTicket = 0;

	/**
	 * 墙票
	 */
	private Integer wallTicket = 0;

	/**
	 * 墙币
	 */
	private Integer coin = 0;

	/**
	 * 分类
	 */
	private Long typeId = 0L;

	/**
	 * 订阅数
	 */
	private Integer subscribeCount = 0;

	/**
	 * 点击次数
	 */
	private Integer clickCount = 0;

	/**
	 * 简介
	 */
	@Size(max = 200, message = "简介不能超过200字")
	private String description = "";

	/**
	 * foreword
	 */
	@Size(max = 100, message = "寄语不能超过200字")
	private String foreword = "";

	private WorksAuthorizationStatusEnum authorizationStatus = WorksAuthorizationStatusEnum.NONE;

	private Long topicId = 0L;

	private List<WorksTagDto> tags = new ArrayList<>();

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public Integer getWordCount() {
		return wordCount;
	}

	public void setWordCount(Integer wordCount) {
		this.wordCount = wordCount;
	}

	public WorksUpdateStatusEnum getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(WorksUpdateStatusEnum updateStatus) {
		this.updateStatus = updateStatus;
	}

	public WorksContractStatusEnum getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(WorksContractStatusEnum contractStatus) {
		this.contractStatus = contractStatus;
	}

	public WorksShowStatusEnum getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(WorksShowStatusEnum showStatus) {
		this.showStatus = showStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCollectCount() {
		return collectCount;
	}

	public void setCollectCount(Integer collectCount) {
		this.collectCount = collectCount;
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

	public Integer getCoin() {
		return coin;
	}

	public void setCoin(Integer coin) {
		this.coin = coin;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Integer getSubscribeCount() {
		return subscribeCount;
	}

	public void setSubscribeCount(Integer subscribeCount) {
		this.subscribeCount = subscribeCount;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getNovelId() {
		return novelId;
	}

	public void setNovelId(Long novelId) {
		this.novelId = novelId;
	}

	public String getForeword() {
		return foreword;
	}

	public void setForeword(String foreword) {
		this.foreword = foreword;
	}

	public WorksAuthorizationStatusEnum getAuthorizationStatus() {
		return authorizationStatus;
	}

	public void setAuthorizationStatus(WorksAuthorizationStatusEnum authorizationStatus) {
		this.authorizationStatus = authorizationStatus;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public List<WorksTagDto> getTags() {
		return tags;
	}

	public void setTags(List<WorksTagDto> tags) {
		this.tags = tags;
	}
}
