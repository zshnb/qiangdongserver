package com.qiangdong.reader.comic_chapter;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.ComicChapter;
import com.qiangdong.reader.enums.common.WorksChapterReviewStatusEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.comic.UpdateComicChapterReviewStatusRequest;
import com.qiangdong.reader.serviceImpl.ComicChapterServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicChapterServiceUpdateComicChapterReviewTest extends BaseTest {

    @Autowired
    private ComicChapterServiceImpl chapterService;

    @Test
    public void updateReviewStatusSuccessful() {
        UpdateComicChapterReviewStatusRequest request = new UpdateComicChapterReviewStatusRequest();
        request.setChapterId(1L);
        request.setUserId(editorUserId);
        request.setReviewStatus(WorksChapterReviewStatusEnum.REJECT);
        chapterService.updateComicChapterReviewStatus(request);
        ComicChapter entity = chapterService.getById(request.getChapterId());
        assertThat(entity.getReviewStatus()).isEqualTo(WorksChapterReviewStatusEnum.REJECT);
    }

    @Test
    public void updateReviewFailedWhenChapterNoExist() {
        UpdateComicChapterReviewStatusRequest request = new UpdateComicChapterReviewStatusRequest();
        request.setChapterId(100L);
        request.setUserId(editorUserId);
        request.setReviewStatus(WorksChapterReviewStatusEnum.REJECT);
        assertException(InvalidArgumentException.class, () -> {
            chapterService.updateComicChapterReviewStatus(request);
        });
    }

    @Test
    public void updateReviewFailedWhenPermissionDeny() {
        UpdateComicChapterReviewStatusRequest request = new UpdateComicChapterReviewStatusRequest();
        request.setChapterId(100L);
        request.setUserId(userId);
        request.setReviewStatus(WorksChapterReviewStatusEnum.REJECT);
        assertException(PermissionDenyException.class, () -> {
            chapterService.updateComicChapterReviewStatus(request);
        });
    }
}
