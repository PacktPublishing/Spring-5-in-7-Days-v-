package com.demo.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityAppIntializer extends AbstractSecurityWebApplicationInitializer{
	
	public SecurityAppIntializer(){
		super(SecurityConfig.class,RepositoryConfig.class);
	}

}
