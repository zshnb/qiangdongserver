package com.qiangdong.reader.validate.function_area;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.dao.FunctionAreaMapper;
import com.qiangdong.reader.entity.FunctionArea;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.function_area.DeleteFunctionAreaRequest;
import com.qiangdong.reader.validate.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteFunctionAreaValidator extends RequestValidator<DeleteFunctionAreaRequest> {
	@Autowired
	private FunctionAreaMapper functionAreaMapper;

	@Override
	public void validate(DeleteFunctionAreaRequest request) {
		FunctionArea functionArea = functionAreaMapper.selectById(request.getFunctionAreaId());
		if (functionArea == null) {
			throw new InvalidArgumentException("金刚区菜单不存在");
		}
		int count = functionAreaMapper.selectCount(new QueryWrapper<FunctionArea>()
			.eq("parent_id", request.getFunctionAreaId()));
		if (count > 0) {
			throw new InvalidArgumentException("无法删除含有子菜单的菜单");
		}
	}
}
