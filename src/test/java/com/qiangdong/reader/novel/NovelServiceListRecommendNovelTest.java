package com.qiangdong.reader.novel;

import cn.hutool.core.collection.CollectionUtil;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.novel.RecommendNovelDto;
import com.qiangdong.reader.enums.common.RecommendTypeEnum;
import com.qiangdong.reader.request.novel.AddRecommendNovelRequest;
import com.qiangdong.reader.request.novel.ListRecommendNovelRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelServiceListRecommendNovelTest extends BaseTest {
    @Autowired
    private NovelServiceImpl novelService;

    @Test
    public void listNovelSuccessful() {
        LocalDateTime createAt = LocalDateTime.now();
        LocalDateTime endAt = LocalDateTime.now().plusMinutes(1);
        List<Long> novelIds = CollectionUtil.newArrayList(1L);
        AddRecommendNovelRequest request = new AddRecommendNovelRequest();
        request.setUserId(editorUserId);
        request.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
        request.setNovelIds(novelIds);
        request.setCreateAt(createAt);
        request.setEndAt(endAt);
        novelService.addRecommendNovel(request);

        ListRecommendNovelRequest listRecommendNovelRequest = new ListRecommendNovelRequest();
        listRecommendNovelRequest.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
        PageResponse<RecommendNovelDto> response = novelService.listRecommendNovel(listRecommendNovelRequest);
        assertThat(response.getList().size()).isEqualTo(1);
    }

    @Test
    public void listFreeNovelSuccessful() {
        LocalDateTime createAt = LocalDateTime.now();
        LocalDateTime endAt = LocalDateTime.now().plusMinutes(1);
        List<Long> novelIds = CollectionUtil.newArrayList(1L);
        AddRecommendNovelRequest request = new AddRecommendNovelRequest();
        request.setUserId(editorUserId);
        request.setRecommendType(RecommendTypeEnum.FREE);
        request.setNovelIds(novelIds);
        request.setCreateAt(createAt);
        request.setEndAt(endAt);
        novelService.addRecommendNovel(request);

        ListRecommendNovelRequest listRecommendNovelRequest = new ListRecommendNovelRequest();
        listRecommendNovelRequest.setRecommendType(RecommendTypeEnum.FREE);
        PageResponse<RecommendNovelDto> response = novelService.listRecommendNovel(listRecommendNovelRequest);
        assertThat(response.getList().size()).isEqualTo(1);
        assertThat(response.getList().get(0).getEndAt()).isEqualTo(endAt);
    }

    @Test
    public void listAllRecommendTypeNovelSuccess() {
        LocalDateTime createAt = LocalDateTime.now();
        LocalDateTime endAt = LocalDateTime.now().plusMinutes(1);
        List<Long> novelIds = CollectionUtil.newArrayList(1L);
        AddRecommendNovelRequest request = new AddRecommendNovelRequest();
        request.setUserId(editorUserId);
        request.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
        request.setNovelIds(novelIds);
        request.setCreateAt(createAt);
        request.setEndAt(endAt);
        novelService.addRecommendNovel(request);

        request.setRecommendType(RecommendTypeEnum.FREE);
        request.setNovelIds(CollectionUtil.newArrayList(1L));
        novelService.addRecommendNovel(request);

        ListRecommendNovelRequest listRecommendNovelRequest = new ListRecommendNovelRequest();
        PageResponse<RecommendNovelDto> response = novelService.listRecommendNovel(listRecommendNovelRequest);
        assertThat(response.getList().size()).isEqualTo(2);
    }
}
