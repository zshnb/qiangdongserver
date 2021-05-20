package com.qiangdong.reader.type;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dao.TypeMapper;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.enums.type.TypeBelongEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.type.AddOrUpdateTypeRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.TypeServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class TypeServiceAddOrUpdateTest extends BaseTest {

    @Autowired
    private TypeServiceImpl typeService;

    @Autowired
    private TypeMapper typeMapper;

    @Test
    public void addTypeSuccessful() {
        AddOrUpdateTypeRequest request = new AddOrUpdateTypeRequest();
        request.setName("动作");
        request.setParentId(0L);
        request.setBelong(TypeBelongEnum.COMIC);
        Response<Type> response = typeService.addOrUpdateType(request);
        Type entity = typeMapper.selectById(response.getData().getId());
        Assert.assertNotNull(entity);
        Assert.assertEquals(entity.getName(), request.getName());
        Assert.assertTrue(entity.getId() > 0L);
    }

    @Test
    public void updateTypeSuccessful() {
        AddOrUpdateTypeRequest request = new AddOrUpdateTypeRequest();
        request.setTypeId(1L);
        request.setName("动作");
        request.setParentId(0L);
        request.setBelong(TypeBelongEnum.COMIC);
        Response<Type> response = typeService.addOrUpdateType(request);
        Type entity = typeMapper.selectById(response.getData().getId());
        Assert.assertNotNull(entity);
        Assert.assertEquals(request.getTypeId(), entity.getId());
        Assert.assertEquals(request.getName(), entity.getName());
    }

    @Test
    public void addTypeFailedWhenNameExists() {
        AddOrUpdateTypeRequest request = new AddOrUpdateTypeRequest();
        request.setName("分类1");
        request.setBelong(TypeBelongEnum.COMIC);
        Response<Type> response = typeService.addOrUpdateType(request);
        assertException(InvalidArgumentException.class, () -> {
            Response<Type> response1 = typeService.addOrUpdateType(request);
            Assert.assertEquals("作品分类已存在", response1.getMessage());
        });
    }

}
