package com.direct.app.redis.stream.subscribe;

import com.direct.app.mq.MessageHandler;
import org.springframework.data.redis.stream.StreamListener;

public interface RedisMessageHandler extends StreamListener, MessageHandler {
}
