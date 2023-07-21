package com.direct.app.redis.pubsub;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service("UserSignupSubscriber")
public class UserSignupSubscriber implements MessageListener {

	@Override
	public void onMessage(Message message, byte[] bytes) {
		System.out.println("New Message Received with content: " + message);
	}
}
