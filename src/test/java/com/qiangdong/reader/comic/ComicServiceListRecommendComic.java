package com.qiangdong.reader.comic;

import cn.hutool.core.collection.CollectionUtil;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.comic.RecommendComicDto;
import com.qiangdong.reader.enums.common.RecommendTypeEnum;
import com.qiangdong.reader.request.comic.AddRecommendComicRequest;
import com.qiangdong.reader.request.comic.ListRecommendComicRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.ComicServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicServiceListRecommendComic extends BaseTest {
    @Autowired
    private ComicServiceImpl comicService;

    @Test
    public void listComicSuccessful() {
        LocalDateTime createAt = LocalDateTime.now();
        LocalDateTime endAt = LocalDateTime.now().plusMinutes(1);
        List<Long> comicIds = CollectionUtil.newArrayList(1L, 2L);
        AddRecommendComicRequest request = new AddRecommendComicRequest();
        request.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
        request.setComicIds(comicIds);
        request.setCreateAt(createAt);
        request.setEndAt(endAt);
        comicService.addRecommendComic(request);

        ListRecommendComicRequest listRecommendComicRequest = new ListRecommendComicRequest();
        listRecommendComicRequest.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
        PageResponse<RecommendComicDto> response = comicService.listRecommendComic(listRecommendComicRequest);
        assertThat(response.getList().size()).isEqualTo(2);
    }

    @Test
    public void listAllRecommendTypeComicSuccess() {
        LocalDateTime createAt = LocalDateTime.now();
        LocalDateTime endAt = LocalDateTime.now().plusMinutes(1);
        List<Long> novelIds = CollectionUtil.newArrayList(1L);
        AddRecommendComicRequest request = new AddRecommendComicRequest();
        request.setUserId(editorUserId);
        request.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
        request.setComicIds(novelIds);
        request.setCreateAt(createAt);
        request.setEndAt(endAt);
        comicService.addRecommendComic(request);

        request.setRecommendType(RecommendTypeEnum.FREE);
        request.setComicIds(CollectionUtil.newArrayList(1L));
        comicService.addRecommendComic(request);

        ListRecommendComicRequest listRecommendComicRequest = new ListRecommendComicRequest();
        PageResponse<RecommendComicDto> response = comicService.listRecommendComic(listRecommendComicRequest);
        Assert.assertEquals(2, response.getList().size());
    }
}
