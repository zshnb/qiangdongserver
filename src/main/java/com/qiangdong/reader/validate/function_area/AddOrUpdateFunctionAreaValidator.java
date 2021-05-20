package com.qiangdong.reader.validate.function_area;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.dao.FunctionAreaMapper;
import com.qiangdong.reader.entity.FunctionArea;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.function_area.AddOrUpdateFunctionAreaRequest;
import com.qiangdong.reader.validate.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddOrUpdateFunctionAreaValidator extends
	RequestValidator<AddOrUpdateFunctionAreaRequest> {
	@Autowired
	private FunctionAreaMapper functionAreaMapper;

	@Override
	public void validate(AddOrUpdateFunctionAreaRequest request) {
		QueryWrapper<FunctionArea> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name", request.getName());
		if (request.getFunctionAreaId() == 0L) {
			FunctionArea functionArea = functionAreaMapper.selectOne(queryWrapper);
			if (functionArea != null) {
				throw new InvalidArgumentException("金刚区菜单已存在");
			}
		} else {
			FunctionArea functionArea = functionAreaMapper.selectById(request.getFunctionAreaId());
			if (functionArea == null) {
				throw new InvalidArgumentException("金刚区菜单不存在");
			}
			if (!request.getName().equals(functionArea.getName())) {
				functionArea = functionAreaMapper.selectOne(queryWrapper);
				if (functionArea != null) {
					throw new InvalidArgumentException("金刚区菜单已存在");
				}
			}
		}
	}
}
