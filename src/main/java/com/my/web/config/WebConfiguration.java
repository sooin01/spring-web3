package com.my.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
//@EnableSwagger2
@ComponentScan(basePackages = "com.my.web")
@EnableAspectJAutoProxy
@EnableTransactionManagement
@Import(value = JdbcConfiguration.class)
public class WebConfiguration extends WebMvcConfigurerAdapter {

//	@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.select()
//				.apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.any())
//				.build()
//				.apiInfo(apiInfo());
//	}
	
//	public ApiInfo apiInfo() {
//		return new ApiInfoBuilder()
//				.title("Api Documentation")
//				.description("Api Documentation")
//				.version("1.0")
//				.license("Apache License Version 2.0")
//				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
//				.build();
//	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
	 
	    registry.addResourceHandler("/webjars/**")
	    	.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
}
