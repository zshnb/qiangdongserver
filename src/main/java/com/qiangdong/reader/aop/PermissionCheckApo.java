package com.qiangdong.reader.aop;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.annotation.RequireAdmin;
import com.qiangdong.reader.annotation.RequireAuthor;
import com.qiangdong.reader.annotation.RequireAuthorOrEditor;
import com.qiangdong.reader.annotation.RequireEditor;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.user.UserRoleEnum;
import com.qiangdong.reader.exception.InternalException;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(3)
public class PermissionCheckApo {
	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionCheckApo.class);

	@Autowired
	private UserMapper userMapper;

	@Pointcut("execution(public * com.qiangdong.reader.serviceImpl.*.*(..))")
	public void validatePointCut() {}

	@Before("validatePointCut()")
	@SuppressWarnings("unchecked")
	public void checkPermission(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		Method method = Arrays.stream(joinPoint.getSignature()
			.getDeclaringType()
			.getDeclaredMethods())
			.filter(m -> m.getName().equals(joinPoint.getSignature().getName()))
			.findFirst()
			.orElseThrow(() -> new InternalException(String.format("check Permission aop has error. method: %s not found",
				joinPoint.getSignature().getName())));

		if (method.isAnnotationPresent(RequireAuthor.class) || method.isAnnotationPresent(RequireAuthorOrEditor.class) ||
			method.isAnnotationPresent(RequireAdmin.class) || method.isAnnotationPresent(RequireEditor.class)) {
			Class<?> requestClass = args[0].getClass();
			long userId;
			try {
				userId = (long) requestClass.getMethod("getUserId").invoke(args[0]);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				LOGGER.error("request doesn't has getUserId method", e);
				throw new InvalidArgumentException("权限检查异常");
			}
			User user = userMapper.selectOne(new QueryWrapper<User>()
				.select("role")
				.eq("id", userId));
			if (user == null) {
				throw new PermissionDenyException();
			}
			if (method.getAnnotation(RequireAdmin.class) != null) {
				if (!user.getRole().equals(UserRoleEnum.ADMINISTRATOR)) {
					throw new PermissionDenyException();
				}
			} else if (method.getAnnotation(RequireAuthorOrEditor.class) != null) {
				if (!user.getRole().equals(UserRoleEnum.EDITOR) && !user.getRole().equals(UserRoleEnum.AUTHOR)) {
					throw new PermissionDenyException();
				}
			} else if (method.getAnnotation(RequireEditor.class) != null) {
				if (!user.getRole().equals(UserRoleEnum.EDITOR) && !user.getRole().equals(UserRoleEnum.ADMINISTRATOR)) {
					throw new PermissionDenyException();
				}
			} else if (method.getAnnotation(RequireAuthor.class) != null) {
				if (!user.getRole().equals(UserRoleEnum.AUTHOR) && !user.getRole().equals(UserRoleEnum.ADMINISTRATOR)) {
					throw new PermissionDenyException();
				}
			} else {
				throw new PermissionDenyException();
			}
		}
	}
}
