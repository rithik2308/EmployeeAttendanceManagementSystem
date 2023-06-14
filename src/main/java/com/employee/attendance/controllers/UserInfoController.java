package com.employee.attendance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.attendance.entities.UserInfo;
import com.employee.attendance.service.UserInfoService;

@RestController
@RequestMapping("/user")
public class UserInfoController {
	@Autowired
	private UserInfoService service;

	@PostMapping("/add")
	public String addNewUser(@RequestBody UserInfo userInfo) {
		return service.addUser(userInfo);
	}

}
