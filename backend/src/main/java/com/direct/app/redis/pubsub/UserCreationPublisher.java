package com.direct.app.redis.pubsub;

import com.direct.app.redis.ChannelTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Service;

@Service("UserCreationPublisher")
public class UserCreationPublisher implements MessagePublisher {
	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	@Qualifier(ChannelTopics.USER_CREATION_CHANNEL)
	private Topic topic;

	@Override
	public void publish(Object message) {
		redisTemplate.convertAndSend(topic.getTopic(), message);
	}
}
