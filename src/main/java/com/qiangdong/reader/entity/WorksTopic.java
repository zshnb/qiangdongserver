package com.qiangdong.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-23
 */
public class WorksTopic implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	private Long typeId;

	private WorksTypeEnum worksType;

	/**
	 * 标题名称
	 */
	private String name;

	/**
	 * 专题简介
	 */
	private String description;

	/**
	 * 专题封面
	 */
	private String cover;

	private LocalDateTime createAt;

	private LocalDateTime updateAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
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

    public WorksTypeEnum getWorksType() {
        return worksType;
    }

    public void setWorksType(WorksTypeEnum worksType) {
        this.worksType = worksType;
    }

    @Override
	public String toString() {
		return "WorksTopic{" +
			"id=" + id +
			", typeId=" + typeId +
			", name=" + name +
			", description=" + description +
			", cover=" + cover +
			", createAt=" + createAt +
			", updateAt=" + updateAt +
			"}";
	}
}
