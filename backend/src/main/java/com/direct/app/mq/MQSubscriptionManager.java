package com.direct.app.mq;

import org.springframework.data.redis.stream.StreamMessageListenerContainer;

public interface MQSubscriptionManager {
	void addSubscriber(StreamMessageListenerContainer listenerContainer, String queueName, MessageHandler messageHandler, String groupName) throws Exception;
}
