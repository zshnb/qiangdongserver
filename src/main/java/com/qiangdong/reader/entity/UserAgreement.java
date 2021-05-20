package com.qiangdong.reader.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qiangdong.reader.enums.user_agreement.UserAgreementTypeEnum;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-09
 */
public class UserAgreement implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 版本名称
     */
    private String version;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态。1-启用 0-未启用
     */
    private Boolean enabled;

    /**
     * 类型。1-用户协议 2-隐私协议
     */
    private UserAgreementTypeEnum type;

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
     * 删除标识
     */
    private Integer deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public UserAgreementTypeEnum getType() {
        return type;
    }

    public void setType(UserAgreementTypeEnum type) {
        this.type = type;
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
    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "UserAgreement{" +
            "id=" + id +
            ", versionName=" + version +
            ", content=" + content +
            ", status=" + enabled +
            ", type=" + type +
            ", createAt=" + createAt +
            ", updateAt=" + updateAt +
            ", deleted=" + deleted +
        "}";
    }
}
