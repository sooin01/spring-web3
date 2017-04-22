package com.my.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import com.my.app.configuration.aop.AspectConfig;
import com.my.app.configuration.cache.CacheConfig;
import com.my.app.configuration.jdbc.JdbcConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = { "com.my.app.api",
		"com.my.app.web" }, excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class), scopedProxy = ScopedProxyMode.TARGET_CLASS)
@Import(value = { JdbcConfig.class, CacheConfig.class })
public class RootContextConfig {

	@Bean
	public RestTemplate restTemplate() {
		OkHttp3ClientHttpRequestFactory requestFactory = new OkHttp3ClientHttpRequestFactory();
		return new RestTemplate(requestFactory);
	}

	@Bean
	public AspectConfig aspectConfig() {
		return new AspectConfig();
	}

}
