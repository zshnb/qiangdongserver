package com.qiangdong.reader.novel_chapter;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.NovelChapter;
import com.qiangdong.reader.enums.common.WorksChapterReviewStatusEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.novel.UpdateNovelChapterReviewStatusRequest;
import com.qiangdong.reader.serviceImpl.NovelChapterServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelChapterServiceUpdateNovelChapterReviewTest extends BaseTest {

    @Autowired
    private NovelChapterServiceImpl chapterService;

    @Test
    public void updateReviewStatusSuccessful() {
        UpdateNovelChapterReviewStatusRequest request = new UpdateNovelChapterReviewStatusRequest();
        request.setChapterId(1L);
        request.setUserId(editorUserId);
        request.setReviewStatus(WorksChapterReviewStatusEnum.REJECT);
        chapterService.updateNovelChapterReviewStatus(request);
        NovelChapter entity = chapterService.getById(request.getChapterId());
        Assert.assertEquals(request.getReviewStatus().code(), entity.getReviewStatus().code());
    }

    @Test
    public void updateReviewFailedWhenChapterNoExist() {
        UpdateNovelChapterReviewStatusRequest request = new UpdateNovelChapterReviewStatusRequest();
        request.setChapterId(100L);
        request.setUserId(editorUserId);
        request.setReviewStatus(WorksChapterReviewStatusEnum.REJECT);
        assertException(InvalidArgumentException.class, () -> {
            chapterService.updateNovelChapterReviewStatus(request);
        });
    }

    @Test
    public void updateReviewFailedWhenPermissionDeny() {
        UpdateNovelChapterReviewStatusRequest request = new UpdateNovelChapterReviewStatusRequest();
        request.setChapterId(100L);
        request.setUserId(userId);
        request.setReviewStatus(WorksChapterReviewStatusEnum.REJECT);
        assertException(PermissionDenyException.class, () -> {
            chapterService.updateNovelChapterReviewStatus(request);
        });
    }
}
