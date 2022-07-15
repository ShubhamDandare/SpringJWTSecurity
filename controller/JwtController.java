package com.JWTDEMO.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JWTDEMO.entity.JwtRequest;
import com.JWTDEMO.entity.JwtResponse;
import com.JWTDEMO.service.JwtService;
import com.JWTDEMO.utility.JwtUtility;

@RestController
public class JwtController {

	@Autowired
	private JwtUtility utility;

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JwtService service;

	@GetMapping("/hi")
	public String sayhi() {
		return "hello ,good morning";
	}

	@PostMapping("/authenticate")
	public JwtResponse authenticate(@RequestBody JwtRequest request) throws Exception {
		try {
			manager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid Credentials", e);

		}
		final UserDetails userDetails = service.loadUserByUsername(request.getUsername());
		final String token = utility.generateToken(userDetails);
		return new JwtResponse(token);
	}

	@GetMapping("/")
	public String hello() {
		return "Hi this is private url that you are access with token ,congratiulation for Jwt Aunthentication useing Rest Template";
	}
}
