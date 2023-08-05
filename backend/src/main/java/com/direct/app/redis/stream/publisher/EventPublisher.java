package com.direct.app.redis.stream.publisher;

import java.net.UnknownHostException;

public interface EventPublisher<T> {
	void publish(T event, String streamKey) throws UnknownHostException;
}
