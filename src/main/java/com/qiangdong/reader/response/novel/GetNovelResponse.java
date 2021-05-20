package com.qiangdong.reader.response.novel;

import com.qiangdong.reader.dto.novel.NovelChapterDto;
import com.qiangdong.reader.dto.novel.NovelDto;
import com.qiangdong.reader.entity.WorksTag;
import java.util.ArrayList;
import java.util.List;

public class GetNovelResponse {
	private NovelDto novel = new NovelDto();
	private NovelChapterDto lastUpdateChapter = new NovelChapterDto();
	private List<WorksTag> tags = new ArrayList<>();
	private Boolean isInBookStand = false;

	public NovelDto getNovel() {
		return novel;
	}

	public void setNovel(NovelDto novel) {
		this.novel = novel;
	}

	public NovelChapterDto getLastUpdateChapter() {
		return lastUpdateChapter;
	}

	public void setLastUpdateChapter(NovelChapterDto lastUpdateChapter) {
		this.lastUpdateChapter = lastUpdateChapter;
	}

	public List<WorksTag> getTags() {
		return tags;
	}

	public void setTags(List<WorksTag> tags) {
		this.tags = tags;
	}

	public Boolean getInBookStand() {
		return isInBookStand;
	}

	public void setInBookStand(Boolean inBookStand) {
		isInBookStand = inBookStand;
	}
}
