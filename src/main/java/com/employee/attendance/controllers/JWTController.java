package com.employee.attendance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.attendance.entities.AuthRequest;
import com.employee.attendance.service.JWTService;

@RestController
@RequestMapping("/authenticate")
public class JWTController {

	@Autowired
	private JWTService jwtservice;

	@Autowired
	private AuthenticationManager authenticationmanager;

	@PostMapping
	public String authenticateAndGetToken(@RequestBody AuthRequest authrequest) {
		Authentication authentication = authenticationmanager.authenticate(
				new UsernamePasswordAuthenticationToken(authrequest.getUsername(), authrequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtservice.generateToken(authrequest.getUsername());
		} else {
			throw new UsernameNotFoundException("Invalid User Request");
		}
	}
}
