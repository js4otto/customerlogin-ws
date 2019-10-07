package com.payment.api.customerLogin.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.payment.api.customerLogin.constants.Code;
import com.payment.api.customerLogin.exception.BusinessException;
import com.payment.api.customerLogin.repository.CustomerLoginRepository;
import com.payment.api.customerLogin.v1.domain.CustomerLogin;
import com.payment.api.customerLogin.v1.dto.CustomerLoginDto;
import com.payment.api.customerLogin.v1.dto.ErrorDto;
import com.payment.api.customerLogin.v1.dto.ResponseDto;

@Service("customerLoginService")
@Transactional
public class CustomerLoginServiceImpl implements CustomerLoginService {

	@Autowired
	private CustomerLoginRepository customerLoginRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public CustomerLoginDto create(CustomerLoginDto customerLoginDto) {
		try {
			
			Optional<CustomerLogin> clExist = customerLoginRepository.findByUsername(customerLoginDto.getUsername());
			if (clExist.isPresent()) {
				throw new BusinessException(Collections.singletonList(new ErrorDto("username", "Username already exist!", Code.BAD_REQUEST)));
			}
			String customerLoginId = UUID.randomUUID().toString();
			while(customerLoginRepository.findByCustomerLoginId(customerLoginId).isPresent()) {
				customerLoginId = UUID.randomUUID().toString();
			}
			
			CustomerLogin customerLogin = new CustomerLogin();
			customerLogin.setUsername(customerLoginDto.getUsername());
			customerLogin.setCustomerLoginId(customerLoginId);
			customerLogin.setCustomerId(customerLoginDto.getCustomerId());
			customerLogin.setPassword(bCryptPasswordEncoder.encode(customerLoginDto.getPassword()));
			customerLoginRepository.save(customerLogin);
			
			customerLoginDto.setCustomerLoginId(customerLoginId);
			customerLoginDto.setUsername(null);
			customerLoginDto.setPassword(null);
		} catch(Exception e) {
			throw e;
		}
		return customerLoginDto;
	}

	@Override
	public ResponseDto<CustomerLoginDto> login(CustomerLoginDto customerLoginDto) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<CustomerLogin> clOptional = customerLoginRepository.findByUsername(username);
		if (!clOptional.isPresent()) {
			throw new UsernameNotFoundException(username);
		}
		CustomerLogin customerLogin = clOptional.get();
		return new User(customerLogin.getUsername(), customerLogin.getPassword(), true, true, true, true, new ArrayList<>());
	}

	@Override
	public CustomerLoginDto findByUsername(String username) {
		CustomerLoginDto customerDto = null;
		try {
			Optional<CustomerLogin> clOpl = customerLoginRepository.findByUsername(username);
			if (!clOpl.isPresent()) {
				throw new UsernameNotFoundException(username);
			}
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration()
			  .setMatchingStrategy(MatchingStrategies.STRICT);
			customerDto = modelMapper.map(clOpl.get(), CustomerLoginDto.class);
//			customerDto.setCustomerId(clOpl.get().getCustomerId());
			
		} catch(Exception e) {
			throw e;
		}
		return customerDto;
	}

}
