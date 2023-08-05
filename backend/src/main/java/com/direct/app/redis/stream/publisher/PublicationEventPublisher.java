package com.direct.app.redis.stream.publisher;

import com.direct.app.io.dto.PublicationDto;
import com.direct.app.redis.stream.publisher.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;

@Service
@Slf4j
public class PublicationEventPublisher implements EventPublisher<PublicationDto> {
	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public void publish(PublicationDto event, String streamKey) throws UnknownHostException {
		try {
			ObjectRecord<String, PublicationDto> record = StreamRecords
					.newRecord()
					.ofObject(event)
					.withStreamKey(streamKey);
			RecordId recordId = redisTemplate.opsForStream().add(record);
			if (recordId == null) {
				log.error("");
			}
			log.info("recordID {}", recordId.getValue());
		} catch (Exception exception) {
			log.error("Can't publish object {} due to {}", event, exception.getMessage(), exception);
			throw exception;
		}


	}
}
