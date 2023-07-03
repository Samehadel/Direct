package com.direct.app.factories;

import com.direct.app.redis.RedisHash;

public interface RedisHashFactory {
	RedisHash buildRedisHashObject(Class<?> clazz);
}
