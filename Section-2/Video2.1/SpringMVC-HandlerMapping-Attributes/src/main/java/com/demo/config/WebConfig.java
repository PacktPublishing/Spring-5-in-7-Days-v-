package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author ankidaemon
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.demo")
public class WebConfig implements WebMvcConfigurer {
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);
		return resolver;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// forward static resources request to servlet container
		configurer.enable();
	}

	@Autowired
	CustomHandlerInterceptor customHandlerInterceptor;
	
	/*@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(customHandlerInterceptor)
		.addPathPatterns("/info")
		.excludePathPatterns("/downTime");
	}
*/
	/*@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping reqHandlerMapping = new RequestMappingHandlerMapping();
		reqHandlerMapping.setInterceptors(customHandlerInterceptor);
		return reqHandlerMapping;
	}*/

	@Bean
	public CustomHandlerInterceptor customHandlerInterceptor() {
		return new CustomHandlerInterceptor();
	}
}
