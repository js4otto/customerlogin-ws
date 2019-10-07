package com.payment.api.customerLogin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.payment.api.customerLogin.service.CustomerLoginService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private Environment environment;
	@Autowired
	private CustomerLoginService customerLoginService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.authorizeRequests()
		.antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip"))
		.and()
		.formLogin().loginProcessingUrl(environment.getProperty("login.url.path"))
		.and()
		.addFilter(getAuthenticationFilter());
		
		http.headers().frameOptions().disable();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	public AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(), environment, customerLoginService);
		authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
		return authenticationFilter;
	}
	
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customerLoginService).passwordEncoder(bCryptPasswordEncoder);
	}

}
