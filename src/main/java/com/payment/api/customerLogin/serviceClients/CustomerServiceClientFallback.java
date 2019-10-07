package com.payment.api.customerLogin.serviceClients;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.payment.api.customerLogin.v1.dto.CustomerDto;
import com.payment.api.customerLogin.v1.dto.ResponseDto;

@Component
public class CustomerServiceClientFallback implements CustomerServiceClient {

	@Override
	public ResponseEntity<ResponseDto<CustomerDto>> findByCustomerId(String customerId) {
		// TODO Auto-generated method stub
		return null;
	}

}
