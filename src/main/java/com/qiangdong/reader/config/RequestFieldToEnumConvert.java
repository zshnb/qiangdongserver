package com.qiangdong.reader.config;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.qiangdong.reader.enums.BaseEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import java.util.Map;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RequestFieldToEnumConvert<T extends BaseEnum> implements Converter<Integer, T> {
	private Map<Integer, T> enumMap = MapUtil.newHashMap();

	public RequestFieldToEnumConvert(Class<T> enumType) {
		T[] enums = enumType.getEnumConstants();
		for (T e : enums) {
			enumMap.put(e.code(), e);
		}
	}

	public RequestFieldToEnumConvert() {}

	@Override
	public T convert(Integer source) {
		T t = enumMap.get(source);
		if (ObjectUtil.isNull(t)) {
			throw new InvalidArgumentException("枚举类型不存在");
		}
		return t;
	}
}
