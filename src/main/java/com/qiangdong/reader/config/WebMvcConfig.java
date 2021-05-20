package com.qiangdong.reader.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.qiangdong.reader.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	private final JwtInterceptor jwtInterceptor;
	private final JwtConfig jwtConfig;
	private final EnumSerializer enumSerializer;

	public WebMvcConfig(JwtInterceptor jwtInterceptor, JwtConfig jwtConfig, EnumSerializer enumSerializer) {
		this.jwtInterceptor = jwtInterceptor;
		this.jwtConfig = jwtConfig;
		this.enumSerializer = enumSerializer;
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverterFactory(new RequestFieldToEnumConvertFactory());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if (jwtConfig.isEnable()) {
			registry.addInterceptor(jwtInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns(jwtConfig.getExcludePaths());
		}
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.stream().filter(converter->converter instanceof MappingJackson2HttpMessageConverter).forEach(converter->{
			MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter)converter;
			SimpleModule simpleModule = new SimpleModule();
			simpleModule.addSerializer(enumSerializer);
			jsonConverter.getObjectMapper().registerModule(simpleModule);
		});
	}
}