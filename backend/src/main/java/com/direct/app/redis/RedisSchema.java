package com.direct.app.redis;

public class RedisSchema {
	private static final String DEFAULT_GLOBAL_PREFIX = "direct";
	private static final String USER_DOMAIN = "user";

	public static String getUserHashKey(){
		return DEFAULT_GLOBAL_PREFIX + ":" + USER_DOMAIN;
	}

	public static String getUserHashKey(Long id){
		if(id == null){
			return null;
		}

		return DEFAULT_GLOBAL_PREFIX + ":user:" + id;
	}

	public static String getUserRoleKey(Long id){
		if(id == null){
			return null;
		}

		return DEFAULT_GLOBAL_PREFIX + ":user:role:" + id;
	}

	public static String getUserIdKey(String username){
		if(username == null){
			return null;
		}

		return DEFAULT_GLOBAL_PREFIX + ":user:username:" + username;
	}
}
