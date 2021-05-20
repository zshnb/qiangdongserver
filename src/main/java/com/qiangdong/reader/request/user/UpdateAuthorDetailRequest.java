package com.qiangdong.reader.request.user;

import com.qiangdong.reader.enums.user.UserSexEnum;
import com.qiangdong.reader.request.BaseRequest;

public class UpdateAuthorDetailRequest extends BaseRequest {

    private String nickname = "";
    private UserSexEnum sex = UserSexEnum.NONE;
    private String qqAccount = "";
    private String email = "";
    private String address = "";
    private String mobile = "";

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public UserSexEnum getSex() {
        return sex;
    }

    public void setSex(UserSexEnum sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQqAccount() {
        return qqAccount;
    }

    public void setQqAccount(String qqAccount) {
        this.qqAccount = qqAccount;
    }
}
