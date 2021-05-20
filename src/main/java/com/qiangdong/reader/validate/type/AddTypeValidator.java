package com.qiangdong.reader.validate.type;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.dao.TypeMapper;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.type.AddOrUpdateTypeRequest;
import com.qiangdong.reader.validate.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AddTypeValidator extends RequestValidator<AddOrUpdateTypeRequest> {

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public void validate(AddOrUpdateTypeRequest request) {
        Type entity = typeMapper.selectOne(new QueryWrapper<Type>()
            .select("id")
            .eq("parent_id", request.getParentId())
            .eq("name", request.getName())
            .eq("belong", request.getBelong()));
        if (entity != null) {
            throw new InvalidArgumentException("作品分类已存在");
        }
    }
}
