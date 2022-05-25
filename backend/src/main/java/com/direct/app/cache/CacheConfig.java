package com.direct.app.cache;


import org.ehcache.config.CacheConfiguration;
import org.ehcache.core.config.DefaultConfiguration;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import java.util.List;
import java.util.Map;

import static com.direct.app.cache.CacheNames.*;
import static java.time.Duration.ofMinutes;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toMap;
import static org.ehcache.config.builders.CacheConfigurationBuilder.newCacheConfigurationBuilder;
import static org.ehcache.config.builders.ExpiryPolicyBuilder.timeToLiveExpiration;
import static org.ehcache.config.builders.ResourcePoolsBuilder.heap;
import static org.ehcache.config.units.MemoryUnit.MB;

@Configuration
public class CacheConfig extends CachingConfigurerSupport {

	private List<String> cacheNames = asList(
			SIMILAR_USERS
	);

	private final Long HEAP_SIZE = 256L;
	private final Long TIME_TO_LIVE_IN_MIN = 30L;
	public static final int MAX_CACHED_OBJECT_SIZE_MB = 2;


	@Bean
	@Override
	public org.springframework.cache.CacheManager cacheManager() {
		return new JCacheCacheManager(createInMemoryCacheManager());
	}

	private CacheManager createInMemoryCacheManager() {

		CacheConfiguration<?, ?> defaultCacheConfig =
				newCacheConfigurationBuilder(Object.class, Object.class, heap(HEAP_SIZE))
						.withValueSerializer(KryoSerializer.class)
						.withExpiry(timeToLiveExpiration(ofMinutes(TIME_TO_LIVE_IN_MIN)))
						.withSizeOfMaxObjectSize(MAX_CACHED_OBJECT_SIZE_MB, MB)
						.build();

		Map<String, CacheConfiguration<?, ?>> cachesConfiguration = createCachesConfig(defaultCacheConfig);
		EhcacheCachingProvider provider = getCachingProvider();

		DefaultConfiguration configuration = new DefaultConfiguration(cachesConfiguration, getClassLoader());
		return getCacheManager(provider, configuration);
	}

	private Map<String, CacheConfiguration<?, ?>> createCachesConfig(CacheConfiguration<?, ?> defaultCacheConfig) {
		return cacheNames
				.stream()
				.collect(toMap(name -> name, name -> defaultCacheConfig));
	}

	private EhcacheCachingProvider getCachingProvider() {
		return (EhcacheCachingProvider) Caching.getCachingProvider();
	}

	private ClassLoader getClassLoader() {
		return this.getClass().getClassLoader();
	}

	private CacheManager getCacheManager(EhcacheCachingProvider provider, DefaultConfiguration configuration) {
		return provider.getCacheManager(provider.getDefaultURI(), configuration);
	}
}
