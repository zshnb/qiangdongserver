package com.qiangdong.reader.comic;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.enums.common.WorksContractStatusEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.comic.UpdateComicContractStatusRequest;
import com.qiangdong.reader.serviceImpl.ComicServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicServiceUpdateComicContractStatusTest extends BaseTest {

    @Autowired
    private ComicServiceImpl comicService;

    @Test
    public void updateComicContractStatusSuccessful() {
        UpdateComicContractStatusRequest request = new UpdateComicContractStatusRequest();
        request.setUserId(editorUserId);
        request.setComicId(1L);
        request.setContractStatus(WorksContractStatusEnum.UNSIGNED);
        Comic comic = comicService.updateComicContractStatus(request).getData();
        assertThat(comic.getId()).isEqualTo(1L);
        assertThat(comic.getContractStatus()).isEqualTo(WorksContractStatusEnum.UNSIGNED);
    }

    @Test
    public void updateComicContractStatusFailedWhenComicNoExist() {
        UpdateComicContractStatusRequest request = new UpdateComicContractStatusRequest();
        request.setUserId(editorUserId);
        request.setComicId(1000L);
        request.setContractStatus(WorksContractStatusEnum.UNSIGNED);
        assertException(InvalidArgumentException.class, () -> comicService.updateComicContractStatus(request));
    }

    @Test
    public void updateComicContractStatusFailedWhenPermissionDeny() {
        UpdateComicContractStatusRequest request = new UpdateComicContractStatusRequest();
        request.setComicId(1L);
        request.setUserId(authorUserId);
        request.setContractStatus(WorksContractStatusEnum.UNSIGNED);
        assertException(PermissionDenyException.class, () -> comicService.updateComicContractStatus(request));
    }
}
