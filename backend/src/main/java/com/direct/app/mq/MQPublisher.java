package com.direct.app.mq;

public interface MQPublisher {
	boolean publish(Object event, String queueName) throws Exception;
}
