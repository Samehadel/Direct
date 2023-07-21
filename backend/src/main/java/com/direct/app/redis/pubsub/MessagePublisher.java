package com.direct.app.redis.pubsub;

public interface MessagePublisher {
	void publish(Object message);
}
