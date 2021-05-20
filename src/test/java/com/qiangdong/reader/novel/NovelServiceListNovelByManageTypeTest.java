package com.qiangdong.reader.novel;


import static org.mockito.ArgumentMatchers.any;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.novel.NovelByManageTypeDto;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.user.UserSexEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.novel.ListNovelByManageTypeRequest;
import com.qiangdong.reader.request.user.AddOrUpdateUserEditorRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import com.qiangdong.reader.utils.NovelUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;


public class NovelServiceListNovelByManageTypeTest extends BaseTest {

    @Autowired
    private NovelServiceImpl novelService;

    @SpyBean
    private NovelUtil novelUtil;

    @Before
    public void beforeMock() {
        Mockito.doNothing().when(novelUtil).indexNovel(any(), any());
    }

    @Test
    public void listNovelByManageTypeSuccessful(){
        addNovelRequest.setTypeId(3L);
        novelService.addOrUpdateNovel(addNovelRequest);
        ListNovelByManageTypeRequest request = new ListNovelByManageTypeRequest();
        request.setUserId(editorUserId);
        request.setSex(UserSexEnum.WOMAN);
        PageResponse<NovelByManageTypeDto> response = novelService.listNovelByManageType(request, new User());
        assertThat(response.getList().size()).isEqualTo(1);
        assertThat(response.getList().get(0).getNickName()).isEqualTo("author 1");
    }

    @Test
    public void listNovelByManageTypeFailedWhenPermissionDeny(){
        ListNovelByManageTypeRequest request = new ListNovelByManageTypeRequest();
        request.setUserId(userId);
        request.setSex(UserSexEnum.WOMAN);
        assertException(PermissionDenyException.class, () ->
            novelService.listNovelByManageType(request, new User()));
    }
}
