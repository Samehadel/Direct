package com.direct.app.mq;

public interface MQSubscriptionManager {
	void addSubscriber(String queueName, MessageHandler messageHandler, String groupName) throws Exception;
}
