package com.direct.app.config;

import com.direct.app.redis.ChannelTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

@Configuration
public class RedisPubSubConfig {

	@Autowired
	private RedisConnectionFactory connectionFactory;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	@Qualifier("UserSignupSubscriber")
	private MessageListener userSignupSubscriber;


	@Bean(ChannelTopics.USER_CREATION_CHANNEL)
	public Topic userCreationTopic() {
		return new ChannelTopic(ChannelTopics.USER_CREATION_CHANNEL);
	}

	@Bean
	public RedisMessageListenerContainer redisContainer() {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(userSignupSubscriber, userCreationTopic());
		return container;
	}
}
