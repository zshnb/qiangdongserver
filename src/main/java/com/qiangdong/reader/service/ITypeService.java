package com.qiangdong.reader.service;

import com.qiangdong.reader.annotation.Validation;
import com.qiangdong.reader.dto.TypeDto;
import com.qiangdong.reader.entity.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.type.AddOrUpdateTypeRequest;
import com.qiangdong.reader.request.type.DeleteTypeRequest;
import com.qiangdong.reader.request.type.ListTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
public interface ITypeService extends IService<Type> {

    PageResponse<TypeDto> listTypeByBelong(ListTypeRequest request);

    Response<Type> addOrUpdateType(AddOrUpdateTypeRequest request);

	@Transactional(rollbackFor = RuntimeException.class)
	Response<String> deleteType(DeleteTypeRequest request, Type type);
}
