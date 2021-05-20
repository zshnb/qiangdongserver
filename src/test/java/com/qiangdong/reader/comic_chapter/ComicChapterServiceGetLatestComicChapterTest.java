package com.qiangdong.reader.comic_chapter;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.ComicChapter;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comic.GetLatestComicChapterRequest;
import com.qiangdong.reader.serviceImpl.ComicChapterServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicChapterServiceGetLatestComicChapterTest extends BaseTest {
    @Autowired
    private ComicChapterServiceImpl comicChapterService;

    @Test
    public void getLatestChapterSuccessful() {
        GetLatestComicChapterRequest request = new GetLatestComicChapterRequest();
        request.setComicId(1L);
        ComicChapter comicChapter = comicChapterService.getLatestChapter(request, new Comic()).getData();
        assertThat(comicChapter.getIndex()).isEqualTo(2);
    }

    @Test
    public void getLatestChapterFailedWhenComicNotExist() {
        assertException(InvalidArgumentException.class, () ->
            comicChapterService.getLatestChapter(new GetLatestComicChapterRequest(), new Comic()));
    }
}
