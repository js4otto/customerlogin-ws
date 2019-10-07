package com.payment.api.customerLogin.v1.controllers;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.payment.api.customerLogin.constants.Status;
import com.payment.api.customerLogin.facade.CustomerLoginFacade;
import com.payment.api.customerLogin.v1.dto.CustomerLoginDto;
import com.payment.api.customerLogin.v1.dto.ResponseDto;
import com.payment.api.customerLogin.validators.OnUpdate;

@RestController
@RequestMapping("/customerLogin")
public class CustomerLoginController {
	
	@Autowired
	private CustomerLoginFacade customerLoginFacade;
	
	@PostMapping("/login")
	public void login(@Validated({ OnUpdate.class }) @RequestBody CustomerLoginDto customerLoginDto) {}

	@PostMapping()
	public ResponseEntity<ResponseDto<CustomerLoginDto>> create(@Valid @RequestBody CustomerLoginDto customerLoginDto) {
		List<CustomerLoginDto> data = Collections.singletonList(customerLoginFacade.create(customerLoginDto));
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new ResponseDto<CustomerLoginDto>(Status.SUCCESS, String.valueOf(HttpStatus.OK.value()), data, "Operation was successful", 0, 0, 0L));
	}
	
	@DeleteMapping("/{customerId}")
	public @ResponseBody void delete(@PathVariable String customerId) {
		customerLoginFacade.delete(customerId);
	}
	
}
