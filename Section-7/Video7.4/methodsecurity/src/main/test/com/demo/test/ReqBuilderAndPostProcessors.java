package com.demo.test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.demo.config.RepositoryConfig;
import com.demo.config.SecurityConfig;
import com.demo.config.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityConfig.class,RepositoryConfig.class,WebConfig.class})
@WebAppConfiguration
public class ReqBuilderAndPostProcessors {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .defaultRequest(get("/").with(user("anyUser").roles("anyRole")))
                .build();
    }
    
    @Test
    public void getUpdatePage() throws Exception {
        mvc.perform(
        		post("/updatePage")
        		.with(csrf())
        		.param("id", "1")
        		.with(httpBasic("admin","adminPassword"))
        		)
        .andExpect((status().isOk()));
    }
      
	@Test
    public void getHome() throws Exception {
    	mvc.perform(get("/").with(anonymous()))
    	.andExpect((status().isUnauthorized()));
    }  
    
    @Test
    public void performLoginDefault() throws Exception {
    	mvc.perform(formLogin()).andExpect((redirectedUrl("/login?error")));
    }
        
    @Test
    public void postWithInvalidCsrf() throws Exception {
        mvc
            .perform(post("/logout").with(csrf().useInvalidToken()))
            .andExpect((status().isForbidden()));
    }
    
    @Test
    public void performLoginWithUserPassword() throws Exception {
    	mvc.perform(formLogin("/login").user("admin").password("adminPassword"))
    	.andExpect((redirectedUrl("/")));
    }
    
    @Test
    public void performLoginWithParameterSet() throws Exception {
    	mvc.perform(formLogin("/login").user("admin","employee").password("adminPassword","userPassword"))
    	.andExpect((redirectedUrl("/login?error")));
    }
    
    @Test
    public void performLogout() throws Exception {
        mvc.perform(logout("/logout"))
        .andExpect((redirectedUrl("/login?logout")));
    }  
}
