package com.qiangdong.reader.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.request.BaseRequest;
import org.springframework.stereotype.Component;

@Component
public class PageUtil {
	public <T> Page<T> of(BaseRequest request) {
		return new Page<>(request.getPageNumber(), request.getPageSize());
	}
}
