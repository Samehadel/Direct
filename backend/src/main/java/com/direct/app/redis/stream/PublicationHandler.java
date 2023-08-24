package com.direct.app.redis.stream;

import com.direct.app.redis.stream.subscribe.RedisMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.Record;

@Slf4j
public class PublicationHandler implements RedisMessageHandler /*implements StreamListener<String, ObjectRecord<String, PublicationDto>>*/ {

	@Override
	public void onMessage(Record message) {
		log.info("New Publication Received {}", message.getValue());
	}
}
