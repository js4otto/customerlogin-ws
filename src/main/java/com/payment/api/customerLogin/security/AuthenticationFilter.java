package com.payment.api.customerLogin.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.api.customerLogin.service.CustomerLoginService;
import com.payment.api.customerLogin.v1.dto.CustomerLoginDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private CustomerLoginService customerLoginService;
	private Environment environment;
	private AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager, Environment environment, CustomerLoginService customerLoginService) {
		this.authenticationManager = authenticationManager;
		this.environment = environment;
		this.customerLoginService = customerLoginService;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
		try {
			CustomerLoginDto cred = new ObjectMapper().readValue(req.getInputStream(), CustomerLoginDto.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							cred.getUsername(),
							cred.getPassword(),
							new ArrayList<>()));
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void successfulAuthentication(HttpServletRequest req,
				HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
		String username = ((User) auth.getPrincipal()).getUsername();
		CustomerLoginDto customerLoginDto = customerLoginService.findByUsername(username);
		String token = Jwts.builder()
				.setSubject(customerLoginDto.getCustomerId())
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
				.signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
				.compact();
		res.addHeader("token", token);
		res.addHeader("customerId", customerLoginDto.getCustomerId());
	}
	
}
