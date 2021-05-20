package com.qiangdong.reader.request.user_hobby;

import com.qiangdong.reader.request.BaseRequest;

public class DeleteUserHobbyRequest extends BaseRequest {
    private Long userHobbyId = 0L;

    public Long getUserHobbyId() {
        return userHobbyId;
    }

    public void setUserHobbyId(Long userHobbyId) {
        this.userHobbyId = userHobbyId;
    }
}
