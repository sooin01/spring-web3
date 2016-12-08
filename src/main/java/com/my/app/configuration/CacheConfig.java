package com.my.app.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

@EnableCaching
public class CacheConfig {

	@Bean
	public EhCacheManagerFactoryBean ehCache() {
		EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
		factoryBean.setConfigLocation(new ClassPathResource("ehcache/ehcache.xml"));
		factoryBean.setCacheManagerName("commonCache");
		factoryBean.setShared(true);
		return factoryBean;
	}
	
	@Bean
	public CacheManager cacheManager(EhCacheManagerFactoryBean ehCache) {
		return new EhCacheCacheManager(ehCache.getObject());
	}

}
