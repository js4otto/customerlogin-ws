package com.payment.api.customerLogin.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.payment.api.customerLogin.v1.dto.CustomerLoginDto;
import com.payment.api.customerLogin.v1.dto.ResponseDto;

public interface CustomerLoginService extends UserDetailsService {
	

	CustomerLoginDto create(CustomerLoginDto customerLoginDto);
	
	ResponseDto<CustomerLoginDto> login(CustomerLoginDto customerLoginDto);
	
	CustomerLoginDto findByUsername(String username);

}
