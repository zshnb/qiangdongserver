package com.qiangdong.reader.request.novel;

import com.qiangdong.reader.request.BaseRequest;

public class SearchNovelRequest extends BaseRequest {
	private String keyword = ""; // 作品名和作者笔名

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
