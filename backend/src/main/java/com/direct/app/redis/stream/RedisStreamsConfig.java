package com.direct.app.redis.stream;

import com.direct.app.io.dto.PublicationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.net.UnknownHostException;
import java.time.Duration;

@Configuration
public class RedisStreamsConfig {

	@Autowired
	private RedisTemplate redisTemplate;

	@Bean("publicationListenerContainer")
	public StreamMessageListenerContainer publicationListenerContainer(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
		StreamMessageListenerContainer
				.StreamMessageListenerContainerOptions options = getDefaultOptions()
				.targetType(PublicationDto.class)
				.build();
		StreamMessageListenerContainer listenerContainer = StreamMessageListenerContainer
				.create(redisConnectionFactory, options);
		listenerContainer.start();
		return listenerContainer;
	}

	private StreamMessageListenerContainer.StreamMessageListenerContainerOptionsBuilder getDefaultOptions(){
		return StreamMessageListenerContainer
				.StreamMessageListenerContainerOptions
				.builder()
				.pollTimeout(Duration.ofSeconds(2))
				.hashKeySerializer(RedisSerializer.string())
				.keySerializer(RedisSerializer.string())
				.hashValueSerializer(RedisSerializer.string());
	}
}
