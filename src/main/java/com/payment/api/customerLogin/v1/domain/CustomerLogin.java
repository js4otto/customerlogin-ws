package com.payment.api.customerLogin.v1.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="CUSTOMER_LOGIN")
@Data
public class CustomerLogin implements Serializable {

	private static final long serialVersionUID = -6598251055513854800L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String customerLoginId;
	
	@Column(nullable = false, unique = true, length = 64)
	private String username;

	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private boolean isEnabled;
	
	@Column
	private String customerId;

}
