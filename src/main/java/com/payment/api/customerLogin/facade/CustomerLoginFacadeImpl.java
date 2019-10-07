package com.payment.api.customerLogin.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.payment.api.customerLogin.service.CustomerLoginService;
import com.payment.api.customerLogin.v1.dto.CustomerLoginDto;

@Component
public class CustomerLoginFacadeImpl implements CustomerLoginFacade {

	@Autowired
	private CustomerLoginService customerLoginService;

	@Override
	public CustomerLoginDto create(CustomerLoginDto customerLoginDto) {
		CustomerLoginDto clDto = customerLoginService.create(customerLoginDto);

		return clDto;
	}

	@Override
	public void delete(String customerId) {
		
	}
}
