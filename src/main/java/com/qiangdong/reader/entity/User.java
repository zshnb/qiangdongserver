package com.qiangdong.reader.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qiangdong.reader.dto.user.SecrecyConfig;
import com.qiangdong.reader.enums.user.UserChatStatusEnum;
import com.qiangdong.reader.enums.user.UserCreatorIdentityEnum;
import com.qiangdong.reader.enums.user.UserRoleEnum;
import com.qiangdong.reader.enums.user.UserSexEnum;
import com.qiangdong.reader.enums.user.UserSignStatusEnum;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@TableName("`user`")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 密码 - 非明文
     */
    private String password;

    /**
     * 个人签名
     */
    private String introduction;

    /**
     * 墙币
     */
    private Integer coin;

    private Integer recommendTicket;

    private Integer wallTicket;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    /**
     * 最后一次登录时间
     */
    private LocalDateTime lastLoginTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime updateAt;

    /**
     * 用户角色
     */
    private UserRoleEnum role;

    private String qqAccount;

    private String email;

    private String authName;

    private String idCard;

    private UserSignStatusEnum userSignStatus;

    private String address;

    private UserSexEnum sex;

    private Long levelId;

    private String signature;

    private Long typeId;

    private LocalDateTime birthday;

    private SecrecyConfig secrecyConfig;

    private UserChatStatusEnum chatStatus;

    private UserCreatorIdentityEnum creatorIdentity;

    private Boolean allowCharge;

    /**
     * 逻辑删除
     */
    private Integer deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
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

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public UserSignStatusEnum getUserSignStatus() {
        return userSignStatus;
    }

    public void setUserSignStatus(UserSignStatusEnum userSignStatus) {
        this.userSignStatus = userSignStatus;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public SecrecyConfig getSecrecyConfig() {
        return secrecyConfig;
    }

    public void setSecrecyConfig(SecrecyConfig secrecyConfig) {
        this.secrecyConfig = secrecyConfig;
    }

    public UserChatStatusEnum getChatStatus() {
        return chatStatus;
    }

    public void setChatStatus(UserChatStatusEnum chatStatus) {
        this.chatStatus = chatStatus;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public UserCreatorIdentityEnum getCreatorIdentity() {
        return creatorIdentity;
    }

    public void setCreatorIdentity(UserCreatorIdentityEnum creatorIdentity) {
        this.creatorIdentity = creatorIdentity;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", nickname='" + nickname + '\'' +
            ", avatar='" + avatar + '\'' +
            ", mobile='" + mobile + '\'' +
            ", password='" + password + '\'' +
            ", introduction='" + introduction + '\'' +
            ", coin=" + coin +
            ", recommendTicket=" + recommendTicket +
            ", wallTicket=" + wallTicket +
            ", createAt=" + createAt +
            ", lastLoginTime=" + lastLoginTime +
            ", updateAt=" + updateAt +
            ", role=" + role +
            ", qqAccount='" + qqAccount + '\'' +
            ", email='" + email + '\'' +
            ", authName='" + authName + '\'' +
            ", idCard='" + idCard + '\'' +
            ", userSignStatus=" + userSignStatus +
            ", address='" + address + '\'' +
            ", sex=" + sex +
            ", levelId=" + levelId +
            ", signature='" + signature + '\'' +
            ", typeId=" + typeId +
            ", birthday=" + birthday +
            ", secrecyConfig=" + secrecyConfig +
            ", chatStatus=" + chatStatus +
            ", creatorIdentity=" + creatorIdentity +
            ", deleted=" + deleted +
            '}';
    }

    public Boolean getAllowCharge() {
        return allowCharge;
    }

    public void setAllowCharge(Boolean allowCharge) {
        this.allowCharge = allowCharge;
    }
}
