package com.pe.customer.infrastructure.security.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pe.customer.infrastructure.security.model.User;
import com.pe.customer.infrastructure.security.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User daoUser = userRepository.findByUsername(username);
		// TODO Auto-generated method stub
		if (daoUser == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new org.springframework.security.core.userdetails.User(daoUser.getUsername(), daoUser.getPassword(), daoUser.isEnabled(), true, true, true, 
				new ArrayList<>());
		//return new org.springframework.security.core.userdetails.User(daoUser.getUsername(), daoUser.getPassword(), new ArrayList<>());
	}

}
