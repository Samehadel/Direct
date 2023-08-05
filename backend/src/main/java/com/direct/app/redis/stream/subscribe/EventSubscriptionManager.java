package com.direct.app.redis.stream.subscribe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
@Slf4j
public class EventSubscriptionManager {
	@Autowired
	private StreamMessageListenerContainer listenerContainer;

	@Autowired
	private RedisTemplate redisTemplate;

	public void addSubscriber(String streamKey, StreamListener streamListener) throws UnknownHostException {
		addSubscriber(streamKey, streamListener, null);
	}
	public void addSubscriber(String streamKey, StreamListener streamListener, String groupName) throws UnknownHostException {
		String consumerGroupName = getGroupName(streamKey, groupName);
		createConsumerGroupIfNotExists(streamKey, consumerGroupName);
		Consumer consumer = Consumer.from(consumerGroupName, InetAddress.getLocalHost().getHostName());
		listenerContainer.receiveAutoAck(
				consumer,
				StreamOffset.create(streamKey, ReadOffset.lastConsumed()),
				streamListener);
	}

	private String getGroupName(String streamKey, String groupName){
		if(groupName == null){
			return streamKey + ":default-group";
		} else {
			return streamKey + ":" + groupName;
		}
	}
	private void createConsumerGroupIfNotExists(String streamKey, String groupName){
		ReadOffset offset = ReadOffset.from("0-0");
		redisTemplate.opsForStream().createGroup(streamKey, offset, groupName);
	}
}
