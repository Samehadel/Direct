package com.direct.app.redis.stream.subscribe;

import com.direct.app.mq.MQSubscriptionManager;
import com.direct.app.mq.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamInfo;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RedisSubscriptionManager implements MQSubscriptionManager {
	@Autowired
	private StreamMessageListenerContainer listenerContainer;

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public void addSubscriber(String queueName, MessageHandler messageHandler, String groupName) throws Exception {
		addSubscriber(queueName, (StreamListener) messageHandler, groupName);
	}

	private void addSubscriber(String streamKey, StreamListener streamListener, String groupName) throws UnknownHostException {
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
		if(!consumerGroupExists(streamKey, groupName)){
			ReadOffset offset = ReadOffset.from("0-0");
			redisTemplate.opsForStream().createGroup(streamKey, offset, groupName);
		}
	}

	private boolean consumerGroupExists(String streamKey, String groupName) {
		StreamInfo.XInfoGroups xInfoGroups = redisTemplate.opsForStream().groups(streamKey);
		List<StreamInfo.XInfoGroup> groups = xInfoGroups.stream().collect(Collectors.toList());
		for (StreamInfo.XInfoGroup group : groups) {
			if (group.groupName().equals(groupName)) {
				return true;
			}
		}
		return false;
	}

}
