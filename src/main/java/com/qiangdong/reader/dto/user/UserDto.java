package com.qiangdong.reader.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.qiangdong.reader.entity.Menu;
import com.qiangdong.reader.enums.type.TypeBelongEnum;
import com.qiangdong.reader.enums.user.UserChatStatusEnum;
import com.qiangdong.reader.enums.user.UserCreatorIdentityEnum;
import com.qiangdong.reader.enums.user.UserRoleEnum;
import com.qiangdong.reader.enums.user.UserSexEnum;
import com.qiangdong.reader.enums.user.UserSignStatusEnum;
import java.time.LocalDateTime;
import java.util.List;

public class UserDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id = 0L;
    private String username = "";
    private String nickname = "";
    private String avatar = "";
    private String mobile = "";
    private String introduction = "";
    private Integer coin = 0;
    private Integer recommendTicket = 0;
    private Integer wallTicket = 0;
    private Integer role = UserRoleEnum.USER.code();
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
    @JsonSerialize(using = ToStringSerializer.class)
    private Long typeId = 0L;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime birthday = LocalDateTime.now();
    private TypeBelongEnum belong = TypeBelongEnum.NONE;
    private String typeName = "";
    private String parentTypeName = "";
    private List<Menu> menuList;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createAt;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateAt;
    private UserChatStatusEnum chatStatus;
    private UserCreatorIdentityEnum creatorIdentity;
    private Boolean isExistPassword = false;


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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
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

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserSexEnum getSex() {
        return sex;
    }

    public void setSex(UserSexEnum sex) {
        this.sex = sex;
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

    public Boolean getFollow() {
        return isFollow;
    }

    public void setFollow(Boolean follow) {
        isFollow = follow;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getParentTypeName() {
        return parentTypeName;
    }

    public void setParentTypeName(String parentTypeName) {
        this.parentTypeName = parentTypeName;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public TypeBelongEnum getBelong() {
        return belong;
    }

    public void setBelong(TypeBelongEnum belong) {
        this.belong = belong;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public UserChatStatusEnum getChatStatus() {
        return chatStatus;
    }

    public void setChatStatus(UserChatStatusEnum chatStatus) {
        this.chatStatus = chatStatus;
    }

    public UserCreatorIdentityEnum getCreatorIdentity() {
        return creatorIdentity;
    }

    public void setCreatorIdentity(UserCreatorIdentityEnum creatorIdentity) {
        this.creatorIdentity = creatorIdentity;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Boolean getExistPassword() {
        return isExistPassword;
    }

    public void setExistPassword(Boolean existPassword) {
        isExistPassword = existPassword;
    }
}
