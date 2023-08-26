package com.direct.app.redis.stream.publisher;

import com.direct.app.io.dto.PublicationDto;
import com.direct.app.mq.MQMessage;
import com.direct.app.mq.MQPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PublicationEventPublisher implements MQPublisher {
	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public boolean publish(MQMessage event, String queueName) throws Exception {
		try {
			ObjectRecord<String, PublicationDto> record = StreamRecords
					.newRecord()
					.ofObject((PublicationDto) event)
					.withStreamKey(queueName);
			RecordId recordId = redisTemplate.opsForStream().add(record);
			if (recordId == null) {
				return false;
			}
			log.info("recordID {}", recordId.getValue());
			return true;
		} catch (Exception exception) {
			log.error("Can't publish object {} due to {}", event, exception.getMessage(), exception);
			throw exception;
		}
	}
}
