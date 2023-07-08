package com.direct.app.ws.redis;

import com.direct.app.enumerations.UserRole;
import com.direct.app.io.dto.UserDto;
import com.direct.app.redis.LuaScriptRunner;
import com.direct.app.redis.RedisSchema;
import com.direct.app.redis.RedisTemplateHolder;
import io.lettuce.core.ScriptOutputType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTemplateTest {

	@LocalServerPort
	private int port;

	@Autowired
	private RedisTemplateHolder redisTemplateHolder;

	@Autowired
	private LuaScriptRunner luaScriptRunner;

	@Test
	public void testSaveUser(){
		UserDto user = buildUserDTO(10L);
		redisTemplateHolder.saveHash(user);

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
		return (UserDto) redisTemplateHolder.findHash(id, UserDto.class);
	}

	@Test
	public void testUserRoleCaching() {
		UserDto user = buildUserDTO();
		UserRole role = UserRole.ROLE_ADMIN;

		redisTemplateHolder.saveString(user.getRedisKey(), role.toString());
		Assert.assertEquals(role.toString(), getInMemoryUserRole(user.getId()));
	}


	private String getInMemoryUserRole(Long id) {
		return (String) redisTemplateHolder.findString(RedisSchema.getUserHashKey(id));
	}

	@Test
	public void testLuaScriptExecution() {
		Boolean result = (Boolean) luaScriptRunner.executeLuaScript("checkThenUpdate", ScriptOutputType.BOOLEAN, new String[]{"user:1"}, "45");

		Assert.assertTrue(result);
	}

}
