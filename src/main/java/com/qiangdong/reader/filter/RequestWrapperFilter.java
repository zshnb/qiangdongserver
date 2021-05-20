package com.qiangdong.reader.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.entity.ContentType;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RequestWrapperFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
	                     FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		if (StringUtils.isEmpty(request.getHeader(HttpHeaders.CONTENT_TYPE)) ||
			request.getHeader(HttpHeaders.CONTENT_TYPE).startsWith(ContentType.MULTIPART_FORM_DATA.getMimeType()) ||
			request.getHeader(HttpHeaders.CONTENT_TYPE).startsWith(ContentType.APPLICATION_FORM_URLENCODED.getMimeType())) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			RequestUserIdInjectWrapper wrapper = new RequestUserIdInjectWrapper(request);
			filterChain.doFilter(wrapper, servletResponse);
		}
	}
}
