package com.qiangdong.reader.validate.function_area;

import com.qiangdong.reader.dao.FunctionAreaMapper;
import com.qiangdong.reader.entity.FunctionArea;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.function_area.ListByParentFunctionAreaRequest;
import com.qiangdong.reader.validate.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListByParentFunctionAreaValidator extends RequestValidator<ListByParentFunctionAreaRequest> {
	@Autowired
	private FunctionAreaMapper functionAreaMapper;

	@Override
	public void validate(ListByParentFunctionAreaRequest request) {
		FunctionArea functionArea = functionAreaMapper.selectById(request.getParentId());
		if (functionArea == null) {
			throw new InvalidArgumentException("金刚区菜单不存在");
		}
	}
}
