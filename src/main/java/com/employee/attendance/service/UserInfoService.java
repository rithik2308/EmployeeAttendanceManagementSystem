package com.employee.attendance.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.employee.attendance.entities.UserInfo;
import com.employee.attendance.repositories.UserInfoRepository;

@Service
public class UserInfoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoService.class);
	@Autowired
	private UserInfoRepository repository;
	@Autowired
	private PasswordEncoder passwordencoder;

	public String addUser(UserInfo userInfo) {
		LOGGER.info("Adding an user into the database");
		userInfo.setPassword(passwordencoder.encode(userInfo.getPassword()));
		repository.save(userInfo);
		return "User added";
	}
}
