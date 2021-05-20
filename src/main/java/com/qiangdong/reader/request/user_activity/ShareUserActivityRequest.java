package com.qiangdong.reader.request.user_activity;

import com.qiangdong.reader.dto.user_activity.ShareActivity;
import com.qiangdong.reader.request.BaseRequest;

public class ShareUserActivityRequest extends BaseRequest {
    private ShareActivity shareActivity = new ShareActivity();

    public ShareActivity getShareActivity() {
        return shareActivity;
    }

    public void setShareActivity(ShareActivity shareActivity) {
        this.shareActivity = shareActivity;
    }
}
