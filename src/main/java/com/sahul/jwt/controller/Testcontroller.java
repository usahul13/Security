package com.sahul.jwt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class Testcontroller {
	
	@RequestMapping("/hello")
	public String welcome() {
		return "Welcome";
	}

}