package com.sahul.jwt.Secutity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sahul.jwt.model.User;
import com.sahul.jwt.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceimpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user not found with name: "+username));
		return UserDetailsimpl.buildI(user);
		
	}

}
