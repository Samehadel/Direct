package com.direct.app.cache;

import com.direct.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;


public class UserBasedKeyGenerator implements KeyGenerator {

	@Autowired
	private UserService userService;

	@Override
	public Object generate(Object target, Method method, Object... params) {
		try {
			Long currentUserId = userService.getCurrentUserId();
			return currentUserId;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}