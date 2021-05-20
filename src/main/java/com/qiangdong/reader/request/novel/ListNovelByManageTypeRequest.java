package com.qiangdong.reader.request.novel;


import com.qiangdong.reader.enums.user.UserSexEnum;
import com.qiangdong.reader.request.BaseRequest;

public class ListNovelByManageTypeRequest extends BaseRequest {
    private String levelName = "";
    private UserSexEnum sex = UserSexEnum.NONE;

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public UserSexEnum getSex() {
        return sex;
    }

    public void setSex(UserSexEnum sex) {
        this.sex = sex;
    }
}
