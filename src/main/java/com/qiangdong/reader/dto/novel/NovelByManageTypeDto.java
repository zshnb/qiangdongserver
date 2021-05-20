package com.qiangdong.reader.dto.novel;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qiangdong.reader.enums.common.WorksContractStatusEnum;
import com.qiangdong.reader.enums.common.WorksShowStatusEnum;
import com.qiangdong.reader.enums.user.UserSexEnum;

public class NovelByManageTypeDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long novelId = 0L;
    private String novelName = "";
    private WorksShowStatusEnum showStatus = WorksShowStatusEnum.NORMAL;
    private WorksContractStatusEnum contractStatus = WorksContractStatusEnum.NONE;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorId = 0L;
    private String typeName = "";
    private String nickName = "";
    private UserSexEnum sex = UserSexEnum.NONE;
    private String levelName = "";
    @JsonSerialize(using = ToStringSerializer.class)
    private Long chapterId = 0L;
    private String title = "";
    private Integer index = 0;

    public Long getNovelId() {
        return novelId;
    }

    public void setNovelId(Long novelId) {
        this.novelId = novelId;
    }

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    public WorksShowStatusEnum getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(WorksShowStatusEnum showStatus) {
        this.showStatus = showStatus;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public UserSexEnum getSex() {
        return sex;
    }

    public void setSex(UserSexEnum sex) {
        this.sex = sex;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public WorksContractStatusEnum getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(WorksContractStatusEnum contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

}
