package com.qiangdong.reader.novel_chapter;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.novel.NovelChapterCatalogItemDto;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.novel.ListNovelChapterCatalogRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.NovelChapterServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelChapterServiceListNovelChapterCatalogTest extends BaseTest {

    @Autowired
    private NovelChapterServiceImpl chapterService;

    @Test
    public void listNovelChapterCatalogItemSuccessful() {
        ListNovelChapterCatalogRequest request = new ListNovelChapterCatalogRequest();
        request.setNovelId(1L);
        request.setUserId(editorUserId);
        PageResponse<NovelChapterCatalogItemDto> response = chapterService.listNovelChapterCatalog(request);
        Assert.assertEquals(2, response.getList().size());
        Assert.assertEquals("novel chapter 1", response.getList().get(0).getTitle());
    }

    @Test
    public void listNovelChapterCatalogItemFailedWhenNovelNoExist(){
        ListNovelChapterCatalogRequest request = new ListNovelChapterCatalogRequest();
        request.setUserId(editorUserId);
        request.setNovelId(15L);
        assertException(InvalidArgumentException.class, () -> chapterService.listNovelChapterCatalog(request));
    }

    @Test
    public void listNovelChapterCatalogItemFailedWhenPermissionDeny(){
        ListNovelChapterCatalogRequest request = new ListNovelChapterCatalogRequest();
        request.setUserId(userId);
        request.setNovelId(15L);
        assertException(PermissionDenyException.class, () -> chapterService.listNovelChapterCatalog(request));
    }
}
