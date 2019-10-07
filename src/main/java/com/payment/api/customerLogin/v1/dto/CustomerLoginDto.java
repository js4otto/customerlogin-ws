package com.payment.api.customerLogin.v1.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.payment.api.customerLogin.validators.OnUpdate;

import lombok.Data;

@Data
public class CustomerLoginDto implements Serializable {

	private static final long serialVersionUID = -4338606291318624412L;
	private String customerLoginId;
	@NotNull(groups = { OnUpdate.class }, message = "Username is cannot be null")
	@NotEmpty(groups = { OnUpdate.class }, message = "Username cannot be empty")
	private String username;
	@NotNull(groups = { OnUpdate.class }, message = "Password is cannot be null")
	@NotEmpty(groups = { OnUpdate.class }, message = "Password cannot be empty")
	private String password;
	private boolean isEnabled;
	@NotNull(message = "Customer Id is cannot be null")
	@NotEmpty(message = "Customer Id cannot be empty")
	private String customerId;

}
