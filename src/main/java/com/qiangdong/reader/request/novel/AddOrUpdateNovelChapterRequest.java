package com.qiangdong.reader.request.novel;

import com.qiangdong.reader.enums.common.WorksChapterReviewStatusEnum;
import com.qiangdong.reader.enums.common.WorksChapterTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class AddOrUpdateNovelChapterRequest extends BaseRequest {
    private Long novelId = 0L;

    private Long chapterId = 0L;

    private String title = "";

    private Integer wordCount = 0;

    private String txtUrl = "";

    private Double progress = 0.0;

    private WorksChapterTypeEnum type = WorksChapterTypeEnum.NONE;

    private String authorWords = "";

    public Long getNovelId() {
        return novelId;
    }

    public void setNovelId(Long novelId) {
        this.novelId = novelId;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public String getTxtUrl() {
        return txtUrl;
    }

    public void setTxtUrl(String txtUrl) {
        this.txtUrl = txtUrl;
    }

    public WorksChapterTypeEnum getType() {
        return type;
    }

    public void setType(WorksChapterTypeEnum type) {
        this.type = type;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public String getAuthorWords() {
        return authorWords;
    }

    public void setAuthorWords(String authorWords) {
        this.authorWords = authorWords;
    }
}
