package com.qiangdong.reader.novel;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.enums.common.WorksContractStatusEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.novel.UpdateNovelContractStatusRequest;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelServiceUpdateNovelContractStatusTest extends BaseTest {

    @Autowired
    private NovelServiceImpl novelService;

    @Test
    public void updateNovelContractStatusSuccessful() {
        UpdateNovelContractStatusRequest request = new UpdateNovelContractStatusRequest();
        request.setUserId(editorUserId);
        request.setNovelId(1L);
        request.setContractStatus(WorksContractStatusEnum.UNSIGNED);
        Novel novel = novelService.updateNovelContractStatus(request).getData();
        assertThat(novel.getId()).isEqualTo(1L);
        assertThat(novel.getContractStatus()).isEqualTo(WorksContractStatusEnum.UNSIGNED);
    }

    @Test
    public void updateNovelContractStatusFailedWhenComicNoExist() {
        UpdateNovelContractStatusRequest request = new UpdateNovelContractStatusRequest();
        request.setNovelId(1000L);
        request.setUserId(editorUserId);
        request.setContractStatus(WorksContractStatusEnum.UNSIGNED);
        assertException(InvalidArgumentException.class, () -> novelService.updateNovelContractStatus(request));
    }

    @Test
    public void updateNovelContractStatusFailedWhenNoPermission() {
        UpdateNovelContractStatusRequest request = new UpdateNovelContractStatusRequest();
        request.setNovelId(1L);
        request.setUserId(authorUserId);
        request.setContractStatus(WorksContractStatusEnum.UNSIGNED);
        assertException(PermissionDenyException.class, () -> novelService.updateNovelContractStatus(request));
    }
}
