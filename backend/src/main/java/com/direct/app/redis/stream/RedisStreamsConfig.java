package com.direct.app.redis.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.net.UnknownHostException;
import java.time.Duration;

@Configuration
public class RedisStreamsConfig {

	@Autowired
	private RedisTemplate redisTemplate;

	@Bean
	public StreamMessageListenerContainer subscription(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
		StreamMessageListenerContainer
				.StreamMessageListenerContainerOptions options = StreamMessageListenerContainer
				.StreamMessageListenerContainerOptions
				.builder()
				.pollTimeout(Duration.ofSeconds(1))
				.targetType(Object.class)
				.build();
		StreamMessageListenerContainer listenerContainer = StreamMessageListenerContainer
				.create(redisConnectionFactory, options);
		listenerContainer.start();
		return listenerContainer;
	}
}
