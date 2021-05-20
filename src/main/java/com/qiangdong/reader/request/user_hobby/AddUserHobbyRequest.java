package com.qiangdong.reader.request.user_hobby;

import com.qiangdong.reader.annotation.NotNone;
import com.qiangdong.reader.enums.user_hobby.UserHobbyTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class AddUserHobbyRequest extends BaseRequest {
    private String name = "";
    @NotNone(message = "无效的爱好分类")
    private UserHobbyTypeEnum type = UserHobbyTypeEnum.NONE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserHobbyTypeEnum getType() {
        return type;
    }

    public void setType(UserHobbyTypeEnum type) {
        this.type = type;
    }
}
