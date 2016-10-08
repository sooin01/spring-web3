package com.my.web.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInitalizer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(WebConfig.class);
		ctx.setServletContext(servletContext);

		FilterRegistration.Dynamic filter = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter("UTF-8", true));
		filter.addMappingForUrlPatterns(null, false, "/*");
		
		filter = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
		filter.addMappingForUrlPatterns(null, false, "/*");

		ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
		servlet.addMapping("/");
		servlet.setLoadOnStartup(1);
	}

}
