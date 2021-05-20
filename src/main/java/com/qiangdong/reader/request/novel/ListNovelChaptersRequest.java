package com.qiangdong.reader.request.novel;

import com.qiangdong.reader.request.BaseRequest;

public class ListNovelChaptersRequest extends BaseRequest {
	private Long novelId = 0L;

	public Long getNovelId() {
		return novelId;
	}

	public void setNovelId(Long novelId) {
		this.novelId = novelId;
	}
}
