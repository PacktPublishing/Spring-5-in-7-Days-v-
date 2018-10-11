package com.demo.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * @author ankidaemon
 *
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	BasicDataSource dataSource;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(NoOpPasswordEncoder.getInstance())
				.usersByUsernameQuery("select username,password,enabled from t_credential where username=?")
				.authoritiesByUsernameQuery("select username,authority from t_credential_roles where username=?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Access control in Spring security
		http
		.authorizeRequests()
			//.antMatchers("/create","/updatePage","/delete/*").access("hasRole('ADMIN')")
			//.antMatchers("/all").hasAnyRole("LOCAL","ADMIN")
		.anyRequest().authenticated()
		.and()
		.requiresChannel().anyRequest().requiresInsecure();

		http.formLogin().and().httpBasic();
		
		http.logout();                                                                
		
		//Access Denied Page
		http.exceptionHandling().accessDeniedPage("/accessDenied");

	}
}