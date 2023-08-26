package com.direct.app.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.ScheduledLockConfiguration;
import net.javacrumbs.shedlock.spring.ScheduledLockConfigurationBuilder;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.Duration;

@EnableTransactionManagement
@Configuration
public class RedisConfig {

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${app.environment}")
	private String ENV;

	@Value("${taskScheduler.poolSize}")
	private int tasksPoolSize;

	@Value("${taskScheduler.defaultLockMaxDurationMinutes}")
	private int lockMaxDuration;

	@Autowired
	private DataSource dataSource;

	@Bean(destroyMethod = "shutdown")
	public ClientResources clientResources() {
		return DefaultClientResources.create();
	}

	@Bean
	public RedisStandaloneConfiguration redisStandaloneConfiguration() {
		return new RedisStandaloneConfiguration(host, port);
	}

	@Bean
	public ClientOptions clientOptions(){
		return ClientOptions.builder()
				.disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
				.autoReconnect(true)
				.build();
	}

	@Bean
	public LettucePoolingClientConfiguration lettucePoolConfig(ClientOptions options, ClientResources clientResources){
		return LettucePoolingClientConfiguration.builder()
				.poolConfig(new GenericObjectPoolConfig())
				.clientOptions(options)
				.clientResources(clientResources)
				.build();
	}

	@Bean
	public LockProvider lockProvider(RedisConnectionFactory connectionFactory) {
		return new RedisLockProvider(connectionFactory, ENV);
	}

	@Bean
	public ScheduledLockConfiguration taskSchedulerLocker(LockProvider lockProvider) {
		return ScheduledLockConfigurationBuilder
				.withLockProvider(lockProvider)
				.withPoolSize(tasksPoolSize)
				.withDefaultLockAtMostFor(Duration.ofMinutes(lockMaxDuration))
				.build();
	}
	@Bean
	public RedisConnectionFactory connectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration,
													LettucePoolingClientConfiguration lettucePoolConfig) {
		return new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolConfig);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setKeySerializer(RedisSerializer.string());
		template.setHashKeySerializer(RedisSerializer.string());
		template.setStringSerializer(RedisSerializer.string());
		template.setValueSerializer(RedisSerializer.string());
		template.setHashValueSerializer(RedisSerializer.string());
		template.setEnableTransactionSupport(true);
		return template;
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws SQLException {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public RedisClient redisClient(){
		return RedisClient.create(RedisURI.create(host, port));
	}
}
