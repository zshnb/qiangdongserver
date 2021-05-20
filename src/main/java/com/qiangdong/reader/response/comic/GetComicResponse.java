package com.qiangdong.reader.response.comic;

import com.qiangdong.reader.dto.comic.ComicChapterDto;
import com.qiangdong.reader.dto.comic.ComicDto;
import com.qiangdong.reader.entity.WorksTag;
import java.util.ArrayList;
import java.util.List;

public class GetComicResponse {
	private ComicDto comic = new ComicDto();
	private ComicChapterDto lastUpdateChapter = new ComicChapterDto();
	private Boolean isInBookStand = false;
	private List<WorksTag> tags = new ArrayList<>();

	public ComicDto getComic() {
		return comic;
	}

	public void setComic(ComicDto comic) {
		this.comic = comic;
	}

	public ComicChapterDto getLastUpdateChapter() {
		return lastUpdateChapter;
	}

	public void setLastUpdateChapter(ComicChapterDto lastUpdateChapter) {
		this.lastUpdateChapter = lastUpdateChapter;
	}

	public Boolean getInBookStand() {
		return isInBookStand;
	}

	public void setInBookStand(Boolean inBookStand) {
		isInBookStand = inBookStand;
	}

	public List<WorksTag> getTags() {
		return tags;
	}

	public void setTags(List<WorksTag> tags) {
		this.tags = tags;
	}
}
