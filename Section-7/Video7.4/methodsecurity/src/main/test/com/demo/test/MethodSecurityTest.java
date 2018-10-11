package com.demo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.demo.config.RepositoryConfig;
import com.demo.config.SecurityConfig;
import com.demo.config.WebConfig;
import com.demo.web.HomeController;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = {WebConfig.class,SecurityConfig.class,RepositoryConfig.class})
@WebAppConfiguration
public class MethodSecurityTest	 {
	@Autowired
	public HomeController homeController;
	
	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void expectedTest() {
		homeController.findAll();
	}
	
	@Test
	@WithMockUser(roles={"USER"})
	public void withMockUserOnlyRoleTest() {
		homeController.findByUserName("test");
	}
	
	@Test
	@WithMockUser(username = "admin",authorities={"ROLE_ADMIN"})
	//@WithMockUser(username = "admin",roles={"ADMIN"})
	public void withMockUserTest() {
		homeController.renderHome();
	}
	
	//Below Test will fail as no User with username-xyz exists with bean userDetailsService
	@Test
	@WithUserDetails(value="xyz", userDetailsServiceBeanName="userDetailsService")
	public void withUserDetailsTest() {
		homeController.findAll();
	}
} 
