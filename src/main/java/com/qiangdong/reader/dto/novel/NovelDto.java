package com.qiangdong.reader.dto.novel;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.qiangdong.reader.enums.common.RecommendTypeEnum;
import com.qiangdong.reader.enums.common.WorksAuthorizationStatusEnum;
import com.qiangdong.reader.enums.common.WorksContractStatusEnum;
import com.qiangdong.reader.enums.common.WorksUpdateStatusEnum;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class NovelDto implements Serializable {
	@JsonSerialize(using = ToStringSerializer.class)
	private Long novelId = 0L;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long typeId;
	private String name = "";
	private String typeName = "";
	private String parentTypeName = "";
	private Integer wordCount = 0;
	private Integer clickCount = 0;
	private Integer collectCount = 0;
	private Integer recommendTicket = 0;
	private String authorName = "";
	private String avatar = "";
	@JsonSerialize(using = ToStringSerializer.class)
	private Long authorId = 0L;
	private WorksUpdateStatusEnum updateStatus = WorksUpdateStatusEnum.NONE;
	private String description = "";
	private String cover = "";
	private Integer coin = 0;
	private Integer wallTicket = 0;
	private String foreword = "";
	private WorksAuthorizationStatusEnum authorizationStatus = WorksAuthorizationStatusEnum.NONE;
	private RecommendTypeEnum recommendType = RecommendTypeEnum.NONE;
	private NovelChapterDto lastUpdateChapter = new NovelChapterDto();
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime updateAt;
	private WorksContractStatusEnum contractStatus = WorksContractStatusEnum.UNSIGNED;
	private Integer lastReadChapterIndex = 0;

	public RecommendTypeEnum getRecommendType() {
		return recommendType;
	}

	public void setRecommendType(RecommendTypeEnum recommendType) {
		this.recommendType = recommendType;
	}

	public Long getNovelId() {
		return novelId;
	}

	public void setNovelId(Long novelId) {
		this.novelId = novelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getWordCount() {
		return wordCount;
	}

	public void setWordCount(Integer wordCount) {
		this.wordCount = wordCount;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
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

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public WorksUpdateStatusEnum getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(WorksUpdateStatusEnum updateStatus) {
		this.updateStatus = updateStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Integer getCoin() {
		return coin;
	}

	public void setCoin(Integer coin) {
		this.coin = coin;
	}

	public Integer getWallTicket() {
		return wallTicket;
	}

	public void setWallTicket(Integer wallTicket) {
		this.wallTicket = wallTicket;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		NovelDto novelDto = (NovelDto) o;
		return Objects.equals(novelId, novelDto.novelId) &&
			Objects.equals(name, novelDto.name) &&
			Objects.equals(typeName, novelDto.typeName) &&
			Objects.equals(wordCount, novelDto.wordCount) &&
			Objects.equals(clickCount, novelDto.clickCount) &&
			Objects.equals(collectCount, novelDto.collectCount) &&
			Objects.equals(recommendTicket, novelDto.recommendTicket) &&
			Objects.equals(authorName, novelDto.authorName) &&
			updateStatus == novelDto.updateStatus &&
			Objects.equals(description, novelDto.description) &&
			Objects.equals(cover, novelDto.cover) &&
			Objects.equals(coin, novelDto.coin) &&
			Objects.equals(wallTicket, novelDto.wallTicket);
	}

	@Override
	public int hashCode() {
		return Objects
			.hash(novelId, name, typeName, wordCount, clickCount, collectCount, recommendTicket,
				authorName, updateStatus, description, cover, coin, wallTicket);
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getParentTypeName() {
		return parentTypeName;
	}

	public void setParentTypeName(String parentTypeName) {
		this.parentTypeName = parentTypeName;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public NovelChapterDto getLastUpdateChapter() {
		return lastUpdateChapter;
	}

	public void setLastUpdateChapter(NovelChapterDto lastUpdateChapter) {
		this.lastUpdateChapter = lastUpdateChapter;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

	public WorksContractStatusEnum getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(WorksContractStatusEnum contractStatus) {
		this.contractStatus = contractStatus;
	}

	public Integer getLastReadChapterIndex() {
		return lastReadChapterIndex;
	}

	public void setLastReadChapterIndex(Integer lastReadChapterIndex) {
		this.lastReadChapterIndex = lastReadChapterIndex;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
