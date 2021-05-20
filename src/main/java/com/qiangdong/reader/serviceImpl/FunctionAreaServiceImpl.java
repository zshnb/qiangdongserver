package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.annotation.RequireAdmin;
import com.qiangdong.reader.annotation.Validation;
import com.qiangdong.reader.entity.FunctionArea;
import com.qiangdong.reader.dao.FunctionAreaMapper;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.function_area.AddOrUpdateFunctionAreaRequest;
import com.qiangdong.reader.request.function_area.DeleteFunctionAreaRequest;
import com.qiangdong.reader.request.function_area.ListByParentFunctionAreaRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IFunctionAreaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.validate.function_area.AddOrUpdateFunctionAreaValidator;
import com.qiangdong.reader.validate.function_area.DeleteFunctionAreaValidator;
import com.qiangdong.reader.validate.function_area.ListByParentFunctionAreaValidator;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-10
 */
@Service
public class FunctionAreaServiceImpl extends ServiceImpl<FunctionAreaMapper, FunctionArea>
	implements IFunctionAreaService {

	@Override
	@Validation(AddOrUpdateFunctionAreaValidator.class)
	@Transactional(rollbackFor = RuntimeException.class)
	@RequireAdmin
	public FunctionArea addOrUpdateFunctionArea(AddOrUpdateFunctionAreaRequest request) {
		FunctionArea functionArea;
		if (request.getFunctionAreaId() == 0L) {
			functionArea = new FunctionArea();
			BeanUtils.copyProperties(request, functionArea);
			save(functionArea);
		} else {
			functionArea = getById(request.getFunctionAreaId());
			BeanUtils.copyProperties(request, functionArea);
			updateById(functionArea);
		}
		return functionArea;
	}

	@Override
	@Validation(ListByParentFunctionAreaValidator.class)
	public PageResponse<FunctionArea> listByParentFunctionArea(
		ListByParentFunctionAreaRequest request) {

		QueryWrapper<FunctionArea> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", request.getParentId());
		List<FunctionArea> functionAreas = list(queryWrapper);
		return PageResponse.of(functionAreas, request.getPageSize());
	}

	@Override
	@RequireAdmin
	public PageResponse<FunctionArea> listParentFunctionArea(BaseRequest request) {
		List<FunctionArea> functionAreas = list(new QueryWrapper<FunctionArea>()
			.isNull("parent_id"));
		return PageResponse.of(functionAreas, request.getPageSize());
	}

	@Override
	@Validation(DeleteFunctionAreaValidator.class)
	@Transactional(rollbackFor = RuntimeException.class)
	@RequireAdmin
	public Response<String> deleteFunctionArea(DeleteFunctionAreaRequest request) {
		removeById(request.getFunctionAreaId());
		return Response.ok();
	}

}
