package com.qiangdong.reader.dto.comic;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.qiangdong.reader.enums.common.RecommendTypeEnum;
import com.qiangdong.reader.enums.common.WorksUpdateStatusEnum;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class ComicDto implements Serializable {
	@JsonSerialize(using = ToStringSerializer.class)
	private Long comicId = 0L;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long typeId = 0L;
	private String name = "";
	private String typeName = "";
	private String parentTypeName = "";
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
	private Integer pictureCount = 0;
	private RecommendTypeEnum recommendType = RecommendTypeEnum.NONE;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime updateAt = LocalDateTime.now();
	private ComicChapterDto lastUpdateChapter = new ComicChapterDto();
	private Integer lastReadChapterIndex;

	public RecommendTypeEnum getRecommendType() {
		return recommendType;
	}

	public void setRecommendType(RecommendTypeEnum recommendType) {
		this.recommendType = recommendType;
	}

	public Long getComicId() {
		return comicId;
	}

	public void setComicId(Long comicId) {
		this.comicId = comicId;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ComicDto comicDto = (ComicDto) o;
		return Objects.equals(comicId, comicDto.comicId) &&
			Objects.equals(name, comicDto.name) &&
			Objects.equals(typeName, comicDto.typeName) &&
			Objects.equals(clickCount, comicDto.clickCount) &&
			Objects.equals(collectCount, comicDto.collectCount) &&
			Objects.equals(recommendTicket, comicDto.recommendTicket) &&
			Objects.equals(authorName, comicDto.authorName) &&
			updateStatus == comicDto.updateStatus &&
			Objects.equals(description, comicDto.description) &&
			Objects.equals(cover, comicDto.cover) &&
			Objects.equals(coin, comicDto.coin) &&
			Objects.equals(wallTicket, comicDto.wallTicket);
	}

	@Override
	public int hashCode() {
		return Objects
			.hash(comicId, name, typeName, clickCount, collectCount, recommendTicket,
				authorName, updateStatus, description, cover, coin, wallTicket);
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public Integer getPictureCount() {
		return pictureCount;
	}

	public void setPictureCount(Integer pictureCount) {
		this.pictureCount = pictureCount;
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

	public ComicChapterDto getLastUpdateChapter() {
		return lastUpdateChapter;
	}

	public void setLastUpdateChapter(ComicChapterDto lastUpdateChapter) {
		this.lastUpdateChapter = lastUpdateChapter;
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
