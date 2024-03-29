package com.direct.app.ws.redis;

import com.direct.app.enumerations.UserRole;
import com.direct.app.io.dto.PublicationDto;
import com.direct.app.io.dto.UserDto;
import com.direct.app.mq.MQPublisher;
import com.direct.app.mq.MQSubscriptionManager;
import com.direct.app.mq.MessageHandler;
import com.direct.app.redis.LuaScriptRunner;
import com.direct.app.redis.RedisSchema;
import com.direct.app.redis.RedisHashOperator;
import com.direct.app.redis.pubsub.MessagePublisher;
import com.direct.app.redis.stream.PublicationHandler;
import io.lettuce.core.RedisClient;
import io.lettuce.core.ScriptOutputType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

import static com.direct.app.mq.QueuesNames.JAVA_PUBLICATION_QUEUE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTemplateTest {

	@LocalServerPort
	private int port;

	@Autowired
	private RedisHashOperator redisHashOperator;

	@Autowired
	private LuaScriptRunner luaScriptRunner;

	@Autowired
	public RedisClient client;

	@Autowired
	@Qualifier("UserCreationPublisher")
	private MessagePublisher publisher;

	@Autowired
	private MQPublisher mqPublisher;

	@Autowired
	private MQSubscriptionManager mqSubscriptionManager;

	@Autowired
	@Qualifier("publicationListenerContainer")
	private StreamMessageListenerContainer listenerContainer;


	@Test
	public void testSaveUser() {
		UserDto user = buildUserDTO(10L);
		redisHashOperator.saveHash(user);

		Assert.assertEquals(user, getInMemoryUser(user.getId()));
	}

	private UserDto buildUserDTO() {
		UserDto user = buildUserDTO(1L);
		return user;
	}

	private UserDto buildUserDTO(Long id) {
		UserDto user = new UserDto();

		user.setId(id);
		user.setUsername("sameh@example.com");
		user.setFirstName("Sameh");
		user.setLastName("Adel");
		return user;
	}

	private UserDto getInMemoryUser(Long id) {
		return (UserDto) redisHashOperator.findHash(id, UserDto.class);
	}

	@Test
	public void testUserRoleCaching() {
		UserDto user = buildUserDTO();
		UserRole role = UserRole.ROLE_ADMIN;

		redisHashOperator.saveString(user.getRedisKey(), role.toString());
		Assert.assertEquals(role.toString(), getInMemoryUserRole(user.getId()));
	}


	private String getInMemoryUserRole(Long id) {
		return (String) redisHashOperator.findString(RedisSchema.getUserHashKey(id));
	}

	@Test
	public void testLuaScriptExecution() {
		Boolean result = (Boolean) luaScriptRunner.executeLuaScript("checkThenUpdate", ScriptOutputType.BOOLEAN, new String[]{"user:1"}, "45");

		Assert.assertTrue(result);
	}


	@Test
	public void testPubSubModel() {
		publisher.publish("User Created with ID: 1");
		publisher.publish("User Created with ID: 2");
		publisher.publish("User Created with ID: 3");
		publisher.publish("User Created with ID: 4");
	}
	@Test
	public void testRedisStream() throws Exception {
		String streamKey = JAVA_PUBLICATION_QUEUE;
		PublicationDto publication = PublicationDto.builder()
				.content("Java Developer Job at FIS")
				.link("www.fis.com/jobs/566")
				.build();

		PublicationHandler handler = new PublicationHandler();
		mqSubscriptionManager.addSubscriber(listenerContainer, streamKey, handler, null);

		mqPublisher.publish(publication, streamKey);
	}

}
