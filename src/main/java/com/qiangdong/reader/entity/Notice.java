package com.qiangdong.reader.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qiangdong.reader.enums.notice.NoticeTypeEnum;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 公告表
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-04
 */
public class Notice implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 公告标题
	 */
	private String title;

	/**
	 * 公告的副标题
	 */
	private String subtitle;

	/**
	 * 公告内容
	 */
	private String content;

	/**
	 * 公告类型。1-app。2-创作者
	 */
	private NoticeTypeEnum type;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createAt;

	/**
	 * 修改时间
	 */
	@TableField(update = "now()")
	private LocalDateTime updateAt;

	/**
	 * 发布公告的管理员ID
	 */
	private Long userId;

	/**
	 * 逻辑删除 0-正常 1-已删除
	 */
	private Integer deleted;

	/**
	 * 封面图片
	 */
	private String cover;

	@Override
	public String toString() {
		return "Notice{" +
			"id=" + id +
			", title='" + title + '\'' +
			", subtitle='" + subtitle + '\'' +
			", content='" + content + '\'' +
			", type=" + type +
			", createAt=" + createAt +
			", updateAt=" + updateAt +
			", userId=" + userId +
			", deleted=" + deleted +
			", cover='" + cover + '\'' +
			'}';
	}

	public NoticeTypeEnum getType() {
		return type;
	}

	public void setType(NoticeTypeEnum type) {
		this.type = type;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

}
