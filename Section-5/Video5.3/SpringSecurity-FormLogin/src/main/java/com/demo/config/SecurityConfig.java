package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author ankidaemon
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
     
   @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("ankidaemon").password("{noop}password").roles("USER")
                .and().withUser("test").password("{noop}test").roles("USER");
    }
          
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http
		.authorizeRequests()
			.anyRequest().authenticated()
			.and()
		.formLogin()
		.loginPage("/login").permitAll()
		.and()
		.httpBasic();
    	
    	http
		.logout()                                             
			.logoutUrl("/customlogout") // This must be post method due to CSRF                                 
			.logoutSuccessUrl("/login?logout") // Default is login page                     
			.logoutSuccessHandler(new CustomLogoutSuccessHandler())                              
			.invalidateHttpSession(true) //true by default                                     
			.deleteCookies("JSESSIONID");
    	
    	//http.csrf().disable();
    }
}
