package com.direct.app.redis.stream;

import com.direct.app.io.dto.PublicationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;

@Slf4j
public class PublicationHandler implements StreamListener<String, ObjectRecord<String, PublicationDto>> {

	@Override
	public void onMessage(ObjectRecord<String, PublicationDto> message) {
		log.info("New Publication Received {}", message.getValue());
	}
}
