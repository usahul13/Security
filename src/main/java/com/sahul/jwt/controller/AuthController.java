package com.sahul.jwt.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahul.jwt.Secutity.jwt.jwtUtils;
import com.sahul.jwt.model.ERole;
import com.sahul.jwt.model.Role;
import com.sahul.jwt.model.User;
import com.sahul.jwt.payload.request.Signup;
import com.sahul.jwt.payload.response.MessageResponse;
import com.sahul.jwt.repository.RoleRepository;
import com.sahul.jwt.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",maxAge = 36000)
public class AuthController {
@Autowired
AuthenticationManager authenticationManager;

@Autowired
UserRepository repository;
@Autowired
RoleRepository roleRepository;

@Autowired
PasswordEncoder encoder;

@Autowired
jwtUtils jwtUtils;

@PostMapping("/signup")
public ResponseEntity<?> registerUser(@Valid @RequestBody Signup signup){
	if(repository.existsByUsername(signup.getUsername())) {
		return ResponseEntity.badRequest().body(new MessageResponse("username already taken"));
	}
	
	User user=new User(signup.getUsername(), encoder.encode(signup.getPassword()));
	Set<String> strRole=signup.getRole();
	Set<Role> roles=new HashSet<>();
	if(strRole==null) {
		Role userRole=roleRepository.findByName(ERole.Role_User).orElseThrow(()->new RuntimeException("Role not found"));
		roles.add(userRole);
		
	}
	else {
		strRole.forEach(role->{
			switch(role) {
			case "admin":
				Role userRole=roleRepository.findByName(ERole.Role_admin).orElseThrow(()->new RuntimeException("Role not found"));
				roles.add(userRole);
				break;
				
			case "mod":
				Role modRole=roleRepository.findByName(ERole.Role_moderator).orElseThrow(()->new RuntimeException("Role not found"));
				roles.add(modRole);
				break;
				
			}
			
		});
	}	
		user.setRoles(roles);
		repository.save(user);
		return ResponseEntity.ok(new MessageResponse("user Registered sucessfully"));
	
}

}
