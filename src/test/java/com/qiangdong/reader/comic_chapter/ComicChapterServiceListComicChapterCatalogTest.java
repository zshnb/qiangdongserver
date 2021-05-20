package com.qiangdong.reader.comic_chapter;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.comic.ComicChapterCatalogItemDto;
import com.qiangdong.reader.dto.novel.NovelChapterCatalogItemDto;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.comic.ListComicChapterCatalogRequest;
import com.qiangdong.reader.request.novel.ListNovelChapterCatalogRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.ComicChapterServiceImpl;
import com.qiangdong.reader.serviceImpl.NovelChapterServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicChapterServiceListComicChapterCatalogTest extends BaseTest {

    @Autowired
    private ComicChapterServiceImpl chapterService;

    @Test
    public void listComicChapterCatalogItemSuccessful() {
        ListComicChapterCatalogRequest request = new ListComicChapterCatalogRequest();
        request.setComicId(1L);
        request.setUserId(editorUserId);
        PageResponse<ComicChapterCatalogItemDto> response = chapterService.listComicChapterCatalog(request);
        assertThat(response.getList().size()).isEqualTo(2);
        assertThat(response.getList().get(0).getTitle()).isEqualTo("comic chapter 1-1");
    }

    @Test
    public void listComicChapterCatalogItemFailedWhenNovelNoExist(){
        ListComicChapterCatalogRequest request = new ListComicChapterCatalogRequest();
        request.setUserId(editorUserId);
        request.setComicId(15L);
        assertException(InvalidArgumentException.class, () -> chapterService.listComicChapterCatalog(request));
    }

    @Test
    public void listComicChapterCatalogItemFailedWhenPermissionDeny(){
        ListComicChapterCatalogRequest request = new ListComicChapterCatalogRequest();
        request.setUserId(userId);
        request.setComicId(15L);
        assertException(PermissionDenyException.class, () -> chapterService.listComicChapterCatalog(request));
    }
}
