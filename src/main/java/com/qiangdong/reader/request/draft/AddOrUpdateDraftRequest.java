package com.qiangdong.reader.request.draft;

import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class AddOrUpdateDraftRequest extends BaseRequest {
	private Long draftId = 0L;
	private WorksTypeEnum type = WorksTypeEnum.NONE;
	private Long worksId = 0L;
	private String txtUrl = "";
	private String pictureUrl = "";
	private String title = "";
	private String authorWords = "";
	private int wordCount = 0;
	private int pictureCount = 0;

	public int getWordCount() {
		return wordCount;
	}

	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}

	public int getPictureCount() {
		return pictureCount;
	}

	public void setPictureCount(int pictureCount) {
		this.pictureCount = pictureCount;
	}

	public String getTxtUrl() {
		return txtUrl;
	}

	public void setTxtUrl(String txtUrl) {
		this.txtUrl = txtUrl;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public WorksTypeEnum getType() {
		return type;
	}

	public void setType(WorksTypeEnum type) {
		this.type = type;
	}

	public Long getWorksId() {
		return worksId;
	}

	public void setWorksId(Long worksId) {
		this.worksId = worksId;
	}

	public Long getDraftId() {
		return draftId;
	}

	public void setDraftId(Long draftId) {
		this.draftId = draftId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthorWords() {
		return authorWords;
	}

	public void setAuthorWords(String authorWords) {
		this.authorWords = authorWords;
	}
}
