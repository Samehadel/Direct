package com.direct.app.ws.redis;

import com.direct.app.enumerations.UserRole;
import com.direct.app.io.dto.UserDto;
import com.direct.app.service.RedisSchema;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTemplateTest {

	@LocalServerPort
	private int port;

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	@Test
	public void testSaveUser(){
		HashOperations hashOperations = redisTemplate.opsForHash();
		UserDto user = buildUserDTO();

		String userHashKey = RedisSchema.getUserHashKey();

		hashOperations.put(userHashKey, user.getId(), user);

		Assert.assertEquals(user, getInMemoryUser(user.getId()));
	}

	private UserDto buildUserDTO() {
		UserDto user = new UserDto();

		user.setId(1L);
		user.setUsername("sameh@example.com");
		user.setFirstName("Sameh");
		user.setLastName("Adel");
		return user;
	}

	private UserDto getInMemoryUser(Long id) {
		HashOperations hashOperations = redisTemplate.opsForHash();
		return (UserDto) hashOperations.get(RedisSchema.getUserHashKey(), id);
	}

	@Test
	public void testUserRoleCaching() {
		ValueOperations valueOperations = redisTemplate.opsForValue();
		UserDto user = buildUserDTO();
		UserRole role = UserRole.ROLE_ADMIN;

		valueOperations.set(RedisSchema.getUserRoleKey(user.getId()), role.toString());
		Assert.assertEquals(role.toString(), getInMemoryUserRole(user.getId()));
	}


	private String getInMemoryUserRole(Long id) {
		ValueOperations valueOperations = redisTemplate.opsForValue();
		return (String) valueOperations.get(RedisSchema.getUserRoleKey(id));
	}
}
