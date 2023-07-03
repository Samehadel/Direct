package com.direct.app.redis;

public interface RedisHash {
	void setId(Long id);
	Long getId();
	String getRedisKey();
}
