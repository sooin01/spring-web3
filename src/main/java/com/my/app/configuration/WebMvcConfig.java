package com.my.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import com.my.app.web.common.interceptor.LoggingWebInterceptor;

@Configuration
@EnableWebMvc
//@EnableSwagger2
@ComponentScan(basePackages = "com.my.web")
@EnableAspectJAutoProxy
@EnableTransactionManagement
@Import(value = JdbcConfig.class)
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private ApplicationContext applicationContext;

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
		// springfox
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
	    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	    
	    // css, js, image
	    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	/**
	 * JSP
	 * 
	 * @return
	 */
//	@Bean
//	public InternalResourceViewResolver internalResourceViewResolver() {
//		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//		viewResolver.setViewClass(JstlView.class);
//		viewResolver.setPrefix("/WEB-INF/views/");
//		viewResolver.setSuffix(".jsp");
//		return viewResolver;
//	}
	
	/**
	 * Thymeleaf
	 * 
	 * @return
	 */
	@Bean
	public SpringResourceTemplateResolver templateResolver(){
	    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
	    templateResolver.setApplicationContext(this.applicationContext);
	    templateResolver.setPrefix("/WEB-INF/templates/");
	    templateResolver.setSuffix(".html");
	    templateResolver.setTemplateMode(TemplateMode.HTML);
	    templateResolver.setCacheable(false);
	    return templateResolver;
	}

	/**
	 * Thymeleaf
	 * 
	 * @return
	 */
	@Bean
	public SpringTemplateEngine templateEngine(){
	    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	    templateEngine.setTemplateResolver(templateResolver());
	    templateEngine.setEnableSpringELCompiler(true);
	    return templateEngine;
	}
	
	/**
	 * Thymeleaf
	 * 
	 * @return
	 */
	@Bean
    public ThymeleafViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setTemplateEngine(templateEngine());
        return viewResolver;
    }
	
	@Bean
	public RestTemplate restTemplate() {
		OkHttp3ClientHttpRequestFactory requestFactory = new OkHttp3ClientHttpRequestFactory();
		return new RestTemplate(requestFactory);
	}
	
	@Bean
	public LoggingWebInterceptor loggingWebInterceptor() {
		return new LoggingWebInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loggingWebInterceptor());
	}
	
	@Bean
	public MultipartResolver multipartResolver() {
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    multipartResolver.setMaxUploadSize(1024 * 1024 * 1024);
	    multipartResolver.setDefaultEncoding("utf-8");
	    return multipartResolver;
	}
	
}
