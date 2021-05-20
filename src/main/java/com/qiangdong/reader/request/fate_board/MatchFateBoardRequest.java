package com.qiangdong.reader.request.fate_board;

import com.qiangdong.reader.request.BaseRequest;

public class MatchFateBoardRequest extends BaseRequest {
    private String interest = "";

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}
