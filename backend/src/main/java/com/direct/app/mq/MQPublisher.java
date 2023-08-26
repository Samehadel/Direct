package com.direct.app.mq;

import com.direct.app.io.dto.PublicationDto;

public interface MQPublisher {
	boolean publish(MQMessage event, String queueName) throws Exception;
}
