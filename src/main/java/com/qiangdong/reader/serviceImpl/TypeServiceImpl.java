package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.annotation.Validation;
import com.qiangdong.reader.dto.TypeDto;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.dao.TypeMapper;
import com.qiangdong.reader.enums.type.TypeBelongEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.type.AddOrUpdateTypeRequest;
import com.qiangdong.reader.request.type.DeleteTypeRequest;
import com.qiangdong.reader.request.type.ListTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.ITypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.PageUtil;
import com.qiangdong.reader.validate.type.AddTypeValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements ITypeService {
    private final TypeMapper typeMapper;
    private final PageUtil pageUtil;

    public TypeServiceImpl(TypeMapper typeMapper, PageUtil pageUtil) {
        this.typeMapper = typeMapper;
        this.pageUtil = pageUtil;
    }

    /**
     * 列出分类，小说需列出父分类底下的子分类，漫画不需要
     * */
    @Override
    public PageResponse<TypeDto> listTypeByBelong(ListTypeRequest request) {
        IPage<TypeDto> types = typeMapper.findTypeByBelong(pageUtil.of(request),
            request.getBelong(), request.getParentId());
        return PageResponse.of(types, request.getPageSize());
    }

    @Validation(AddTypeValidator.class)
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Response<Type> addOrUpdateType(AddOrUpdateTypeRequest request) {
        Type type;
        if (request.getTypeId() == 0L) {
            type = new Type();
            BeanUtils.copyProperties(request, type);
            save(type);
        } else {
            type = getById(request.getTypeId());
            BeanUtils.copyProperties(request, type);
            updateById(type);
        }
        return Response.ok(type);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Response<String> deleteType(DeleteTypeRequest request, Type type) {
        if (TypeBelongEnum.NOVEL.equals(type.getBelong()) && type.getParentId() == 0) {
            if (type.getName().equals("男频") || type.getName().equals("女频")){
                throw new InvalidArgumentException("男频女频分类不可删除");
            }
        }
        removeById(request.getTypeId());
        return Response.ok();
    }
}
