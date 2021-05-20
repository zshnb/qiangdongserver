package com.qiangdong.reader.novel;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.novel.NovelAndFirstChapterWithReviewDto;
import com.qiangdong.reader.request.novel.ListNovelAndFirstChapterWithReviewRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelServiceListNovelAndFirstChapterWithReviewTest extends BaseTest {

    @Autowired
    private NovelServiceImpl novelService;

    @Test
    public void listNovelAndFirstChapterWithReviewSuccessful() {
        ListNovelAndFirstChapterWithReviewRequest request = new ListNovelAndFirstChapterWithReviewRequest();
        PageResponse<NovelAndFirstChapterWithReviewDto> response = novelService.listNovelAndFirstChapterWithReview(request);
        Assert.assertEquals(1, response.getList().size());
        Assert.assertEquals("novel2", response.getList().get(0).getNovelName());
    }

}
