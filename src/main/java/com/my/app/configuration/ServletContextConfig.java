package com.my.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import com.my.app.web.common.interceptor.WebInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(
	basePackages = { "com.my.app.api", "com.my.app.web" },
	includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class),
	useDefaultFilters = false
)
public class ServletContextConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// springfox
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
	    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	    
	    // css, js, image
	    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loggingWebInterceptor());
	}
	
	/**
	 * JSP view resolver
	 */
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(0);
		viewResolver.setCache(false);
		return viewResolver;
	}
	
	/**
	 * Thymeleaf template resolver
	 */
	@Bean
	public SpringResourceTemplateResolver templateResolver(){
	    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
	    templateResolver.setApplicationContext(this.applicationContext);
	    templateResolver.setPrefix("/WEB-INF/templates/");
	    templateResolver.setSuffix(".html");
	    templateResolver.setTemplateMode(TemplateMode.HTML);
	    templateResolver.setCacheable(false);
	    templateResolver.setOrder(1);
	    return templateResolver;
	}

	/**
	 * Thymeleaf template engine
	 */
	@Bean
	public SpringTemplateEngine templateEngine(){
	    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	    templateEngine.setTemplateResolver(templateResolver());
	    templateEngine.setEnableSpringELCompiler(true);
	    return templateEngine;
	}
	
	/**
	 * Thymeleaf view resolver
	 */
	@Bean
    public ThymeleafViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setTemplateEngine(templateEngine());
        return viewResolver;
    }
	
	@Bean
	public WebInterceptor loggingWebInterceptor() {
		return new WebInterceptor();
	}
	
//	@Bean
	public MultipartResolver multipartResolver() {
	    return new CommonsMultipartResolver();
	}
	
	@Bean
	public Validator validator() {
		return new LocalValidatorFactoryBean();
	}
	
}
