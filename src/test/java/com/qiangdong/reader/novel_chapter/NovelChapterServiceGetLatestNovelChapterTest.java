package com.qiangdong.reader.novel_chapter;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.NovelChapter;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.novel.GetLatestNovelChapterRequest;
import com.qiangdong.reader.serviceImpl.NovelChapterServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelChapterServiceGetLatestNovelChapterTest extends BaseTest {
    @Autowired
    private NovelChapterServiceImpl novelChapterService;

    @Test
    public void getLatestChapterSuccessful() {
        GetLatestNovelChapterRequest request = new GetLatestNovelChapterRequest();
        request.setNovelId(1L);
        NovelChapter novelChapter = novelChapterService.getLatestChapter(request, new Novel()).getData();
        assertThat(novelChapter.getIndex()).isEqualTo(2);
    }

    @Test
    public void getLatestChapterFailedWhenComicNotExist() {
        assertException(InvalidArgumentException.class, () ->
            novelChapterService.getLatestChapter(new GetLatestNovelChapterRequest(), new Novel()));
    }
}
