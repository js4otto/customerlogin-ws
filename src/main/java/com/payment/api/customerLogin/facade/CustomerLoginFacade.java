package com.payment.api.customerLogin.facade;

import com.payment.api.customerLogin.v1.dto.CustomerLoginDto;

public interface CustomerLoginFacade {

	CustomerLoginDto create(CustomerLoginDto customerLoginDto);
	
	void delete(String customerId);
}
