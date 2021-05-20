package com.qiangdong.reader.type;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.TypeDto;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.enums.type.TypeBelongEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.type.DeleteTypeRequest;
import com.qiangdong.reader.request.type.ListTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.TypeServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class TypeServiceDeleteTypeTest extends BaseTest {

    @Autowired
    private TypeServiceImpl typeService;

    @Test
    public void deleteTypeSuccessful() {
        ListTypeRequest request = new ListTypeRequest();
        request.setBelong(TypeBelongEnum.COMIC);
        PageResponse<TypeDto> response = typeService.listTypeByBelong(request);
        Assert.assertEquals(2, response.getList().size());
        DeleteTypeRequest deleteRequest = new DeleteTypeRequest();
        deleteRequest.setTypeId(6L);
        Response<String> deleteResponse = typeService.deleteType(deleteRequest, new Type());
        PageResponse<TypeDto> listResponse = typeService.listTypeByBelong(request);
        Assert.assertEquals(1, listResponse.getList().size());
    }

    @Test
    public void deleteTypeWhenNoTypeExist(){
        DeleteTypeRequest deleteRequest = new DeleteTypeRequest();
        deleteRequest.setTypeId(1L);
        typeService.deleteType(deleteRequest, new Type());
        assertException(InvalidArgumentException.class,()->{
            Response<String> response = typeService.deleteType(deleteRequest, new Type());
            Assert.assertEquals("分类不存在",response.getMessage());
        });
    }

}
