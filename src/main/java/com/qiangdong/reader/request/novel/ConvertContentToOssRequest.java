package com.qiangdong.reader.request.novel;

import com.qiangdong.reader.request.BaseRequest;

public class ConvertContentToOssRequest extends BaseRequest {
    private String content = "";
    private Long novelId = 0L;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getNovelId() {
        return novelId;
    }

    public void setNovelId(Long novelId) {
        this.novelId = novelId;
    }
}
