package com.qiangdong.reader.type;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.TypeDto;
import com.qiangdong.reader.enums.type.TypeBelongEnum;
import com.qiangdong.reader.request.type.ListTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.TypeServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class TypeServiceListTypeTest extends BaseTest {

    @Autowired
    private TypeServiceImpl typeService;

    @Test
    public void listTypeSuccessful() {
        ListTypeRequest request = new ListTypeRequest();
        request.setBelong(TypeBelongEnum.COMIC);
        PageResponse<TypeDto> response = typeService.listTypeByBelong(request);
        assertThat(response.getList().size()).isEqualTo(2);

        request.setBelong(TypeBelongEnum.NOVEL);
        request.setParentId(1L);
        response = typeService.listTypeByBelong(request);
        assertThat(response.getList().size()).isEqualTo(1);
    }
}
