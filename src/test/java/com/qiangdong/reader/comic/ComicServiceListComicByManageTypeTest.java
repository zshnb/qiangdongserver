package com.qiangdong.reader.comic;


import static org.mockito.ArgumentMatchers.any;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.comic.ComicByManageTypeDto;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.comic.ListComicByManageTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.ComicServiceImpl;
import com.qiangdong.reader.utils.ComicUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

public class ComicServiceListComicByManageTypeTest extends BaseTest {

    @Autowired
    private ComicServiceImpl comicService;

    @SpyBean
    private ComicUtil comicUtil;

    @Before
    public void beforeMock() {
        Mockito.doNothing().when(comicUtil).indexComic(any(), any());
    }

    @Test
    public void listComicByManageTypeSuccessful(){
        addComicRequest.setTypeId(3L);
        comicService.addOrUpdateComic(addComicRequest);
        ListComicByManageTypeRequest request = new ListComicByManageTypeRequest();
        request.setUserId(editorUserId);
        request.setLevelName("LV1");
        PageResponse<ComicByManageTypeDto> response = comicService.listComicByManageType(request, new User());
        assertThat(response.getList().size()).isEqualTo(1);
        assertThat(response.getList().get(0).getComicName()).isEqualTo("comic1");
    }

    @Test
    public void listComicByManageTypeFailedWhenPermissionDeny(){
        ListComicByManageTypeRequest request = new ListComicByManageTypeRequest();
        request.setLevelName("LV1");
        assertException(PermissionDenyException.class,()-> comicService.listComicByManageType(request, new User()));
    }
}
