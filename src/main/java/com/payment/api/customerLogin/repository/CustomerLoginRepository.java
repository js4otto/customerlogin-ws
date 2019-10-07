package com.payment.api.customerLogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.api.customerLogin.v1.domain.CustomerLogin;

public interface CustomerLoginRepository extends JpaRepository<CustomerLogin, Long> {

	Optional<CustomerLogin> findByUsername(String username);
	
	Optional<CustomerLogin> findByCustomerLoginId(String customerLoginId);
}
