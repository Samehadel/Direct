package com.direct.app.redis.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventPublisher {

	@Autowired
	private RedisTemplate redisTemplate;

	private final String PUBLICATION_STREAM_ID = "NOT:JAVA:25";
	public void publish(Object event){
		ObjectRecord<String, Object> record = StreamRecords.newRecord().ofObject(event).withStreamKey(PUBLICATION_STREAM_ID);
		RecordId recordId = redisTemplate.opsForStream().add(record);
		System.out.println("recordID " + recordId.getValue());

		List range = redisTemplate.opsForStream().range(PUBLICATION_STREAM_ID, Range.<String>from(Range.Bound.unbounded()).to(Range.Bound.unbounded()));
		System.out.println(range);
	}
}
