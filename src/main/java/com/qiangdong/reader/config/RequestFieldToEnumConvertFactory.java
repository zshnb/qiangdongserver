package com.qiangdong.reader.config;

import cn.hutool.core.map.MapUtil;
import com.qiangdong.reader.enums.BaseEnum;
import java.util.Map;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

@Component
public class RequestFieldToEnumConvertFactory implements
	ConverterFactory<Integer, BaseEnum> {

	private static final Map<Class<?>, Converter> CONVERTERS = MapUtil.newHashMap();

	@Override
	public <T extends BaseEnum> Converter<Integer, T> getConverter(Class<T> targetType) {
		Converter<Integer, T> converter = CONVERTERS.get(targetType);
		if (converter == null) {
			converter = new RequestFieldToEnumConvert<>(targetType);
			CONVERTERS.put(targetType, converter);
		}
		return converter;
	}
}
