package com.qiangdong.reader.common;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.qiangdong.reader.exception.InternalException;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.response.Response;
import javax.annotation.Nullable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalErrorController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InvalidArgumentException.class)
	public ResponseEntity<Object> handleInvalidArgumentException(InvalidArgumentException e,
	                                                              WebRequest webRequest) {
		return handleExceptionInternal(e, Response.error(e.getMessage()),
			HttpHeaders.EMPTY, BAD_REQUEST, webRequest);
	}

	@ExceptionHandler(InternalException.class)
	public ResponseEntity<Object> handleInternalException(InternalException e,
	                                                      WebRequest webRequest) {
		return handleExceptionInternal(e, Response.error(e.getMessage()),
				HttpHeaders.EMPTY, INTERNAL_SERVER_ERROR, webRequest);
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<Object> handleDuplicateKeyException(DuplicateKeyException e,
	                                                          WebRequest webRequest) {
		return handleExceptionInternal(e, Response.error("服务器异常"),
			HttpHeaders.EMPTY, INTERNAL_SERVER_ERROR, webRequest);
	}

	@ExceptionHandler(PermissionDenyException.class)
	public ResponseEntity<Object> handlePermissionDenyException(PermissionDenyException e,
	                                                            WebRequest webRequest) {
		return handleExceptionInternal(e, null, HttpHeaders.EMPTY, FORBIDDEN, webRequest);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
	                                                              HttpHeaders headers, HttpStatus status,
	                                                              WebRequest webRequest) {
	    String error = e.getBindingResult().getFieldError().getDefaultMessage();
		return handleExceptionInternal(e, Response.error(error),
			HttpHeaders.EMPTY, BAD_REQUEST, webRequest);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(PermissionDenyException e,
	                                              WebRequest webRequest) {
		return handleExceptionInternal(e, Response.error(e.getMessage()),
				HttpHeaders.EMPTY, INTERNAL_SERVER_ERROR, webRequest);
	}
}
