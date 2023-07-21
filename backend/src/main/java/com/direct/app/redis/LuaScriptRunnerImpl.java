package com.direct.app.redis;

import com.direct.app.config.LuaScriptsConfig;
import io.lettuce.core.RedisClient;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import net.jcip.annotations.ThreadSafe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ThreadSafe
@Service
public class LuaScriptRunnerImpl implements LuaScriptRunner {
	private final Logger logger = LogManager.getLogger(RedisHashOperatorImpl.class);
	private final ConcurrentMap<String, String> scriptsSHA = new ConcurrentHashMap<>();

	@Autowired
	private RedisClient redisClient;


	@Override
	public Object executeLuaScript(String scriptName, ScriptOutputType returnType, String[] keys, String... args) {
		String luaScript = getLuaScript(scriptName);

		if (luaScript == null) {
			logger.error("Can execute script [" + scriptName + "] because it's null");
			return null;
		}

		try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
			RedisCommands<String, String> commands = connection.sync();
			String sha = scriptsSHA.get(scriptName);
			if (sha == null) {
				sha = commands.scriptLoad(luaScript);
				scriptsSHA.put(scriptName, sha);
			}
			Object result = commands.evalsha(sha, returnType, keys, args);

			return result;
		} catch (Exception e) {
			logger.error("Error occurred when executing lua script: " + luaScript, e);
			return false;
		}
	}

	private String getLuaScript(String scriptName) {
		logger.info("LuaScriptRunner getLuaScript for script name [" + scriptName + "]");
		ApplicationContext context = new AnnotationConfigApplicationContext(LuaScriptsConfig.class);
		String luaScript = (String) context.getBean(scriptName);

		return luaScript;
	}

}
