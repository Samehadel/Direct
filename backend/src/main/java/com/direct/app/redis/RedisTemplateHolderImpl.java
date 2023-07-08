package com.direct.app.redis;

import com.direct.app.factories.RedisHashFactory;
import io.lettuce.core.RedisClient;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

@Service
public class RedisTemplateHolderImpl implements RedisTemplateHolder {
	private final Logger logger = LogManager.getLogger(RedisTemplateHolderImpl.class);
	private final Jackson2HashMapper mapper = new Jackson2HashMapper(false);

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private RedisHashFactory redisHashFactory;

	@Override
	public boolean saveHash(RedisHash hash) {
		try {
			logger.info("RedisTemplateHolderImpl: saveHash");
			BoundHashOperations<String, Object, Object> boundHashOps = redisTemplate.boundHashOps(hash.getRedisKey());
			boundHashOps.putAll(mapper.toHash(hash));

			return true;
		} catch (Exception exception) {
			logger.error("An error occurred while savingHash due to " + exception.getMessage());
			return false;
		} finally {
			logger.info("RedisTemplateHolderImpl finished saveHash");
		}
	}

	@Override
	public RedisHash findHash(Long id, Class<?> clazz) {
		logger.info("RedisTemplateHolderImpl: findHash for class [" + clazz + "] with ID[" + id + "]");
		RedisHash hash = redisHashFactory.buildRedisHashObject(clazz);
		hash.setId(id);
		Map entries = getHashOps().entries(hash.getRedisKey());

		return (RedisHash) mapper.fromHash(entries);
	}

	private HashOperations getHashOps() {
		HashOperations<String, String, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
		return stringObjectObjectHashOperations;
	}

	private ValueOperations<String, Object> getValueOps() {
		return redisTemplate.opsForValue();
	}

	@Override
	public boolean saveString(String key, Object value) {
		try {
			logger.info("RedisTemplateHolderImpl: saveString");
			getValueOps().set(key, value);

			return true;
		} catch (Exception exception) {
			logger.error("An error occurred while savingHash due to " + exception.getMessage());
			return false;
		} finally {
			logger.info("RedisTemplateHolderImpl finished saveHash");
		}
	}

	@Override
	public Object findString(String key) {
		logger.info("RedisTemplateHolderImpl: findString for key [" + key + "]");
		return getValueOps().get(key);
	}

}
