package com.qiangdong.reader.filter;

import cn.hutool.core.io.IoUtil;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUserIdInjectWrapper extends HttpServletRequestWrapper {
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestUserIdInjectWrapper.class);
	private String body;

	public RequestUserIdInjectWrapper(HttpServletRequest request) {
		super(request);
		try (InputStream inputStream = request.getInputStream()) {
			body = IoUtil.read(inputStream).toString();
		} catch (IOException e) {
			LOGGER.error("load request body error", e);
		}
	}

	@Override
	public ServletInputStream getInputStream() {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
		return new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return false;
			}
			@Override
			public boolean isReady() {
				return false;
			}
			@Override
			public void setReadListener(ReadListener readListener) {
			}
			@Override
			public int read() {
				return byteArrayInputStream.read();
			}
		};

	}

	@Override
	public BufferedReader getReader() {
		return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
