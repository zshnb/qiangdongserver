package com.qiangdong.reader.response.user;

import com.qiangdong.reader.dto.LastUpdateWorksSummaryDto;
import com.qiangdong.reader.dto.comic.ComicAndLastUpdateChapterDto;
import com.qiangdong.reader.dto.novel.NovelAndLastUpdateChapterDto;
import java.util.ArrayList;
import java.util.List;

public class GetAuthorCenterResponse {
	private Integer createCountDay = 0;
	private Long writeCount = 0L;
	private LastUpdateWorksSummaryDto lastUpdateWorksSummaryDto = new LastUpdateWorksSummaryDto();
	private List<NovelAndLastUpdateChapterDto> recentUpdateNovels = new ArrayList<>();
	private List<ComicAndLastUpdateChapterDto> recentUpdateComics = new ArrayList<>();

	public List<NovelAndLastUpdateChapterDto> getRecentUpdateNovels() {
		return recentUpdateNovels;
	}

	public void setRecentUpdateNovels(List<NovelAndLastUpdateChapterDto> recentUpdateNovels) {
		this.recentUpdateNovels = recentUpdateNovels;
	}

	public List<ComicAndLastUpdateChapterDto> getRecentUpdateComics() {
		return recentUpdateComics;
	}

	public void setRecentUpdateComics(List<ComicAndLastUpdateChapterDto> recentUpdateComics) {
		this.recentUpdateComics = recentUpdateComics;
	}

	public Integer getCreateCountDay() {
		return createCountDay;
	}

	public void setCreateCountDay(Integer createCountDay) {
		this.createCountDay = createCountDay;
	}

	public Long getWriteCount() {
		return writeCount;
	}

	public void setWriteCount(Long writeCount) {
		this.writeCount = writeCount;
	}

	public LastUpdateWorksSummaryDto getLastUpdateWorksSummaryDto() {
		return lastUpdateWorksSummaryDto;
	}

	public void setLastUpdateWorksSummaryDto(
		LastUpdateWorksSummaryDto lastUpdateWorksSummaryDto) {
		this.lastUpdateWorksSummaryDto = lastUpdateWorksSummaryDto;
	}
}

