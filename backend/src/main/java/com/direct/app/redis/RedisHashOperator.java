package com.direct.app.redis;

public interface RedisHashOperator {
	boolean saveHash(RedisHash hash);
	RedisHash findHash(Long id, Class<?> clazz);
	boolean saveString(String key, Object value);
	Object findString(String key);
}
