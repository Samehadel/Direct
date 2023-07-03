package com.direct.app.factories;

import com.direct.app.io.dto.UserDto;
import com.direct.app.redis.RedisHash;
import org.springframework.stereotype.Service;

@Service
public class RedisHashFactoryImpl implements RedisHashFactory {

	@Override
	public RedisHash buildRedisHashObject(Class<?> clazz) {
		RedisHash hash = null;

		if(clazz.equals(UserDto.class)){
			hash = new UserDto();
		}

		return hash;
	}
}
