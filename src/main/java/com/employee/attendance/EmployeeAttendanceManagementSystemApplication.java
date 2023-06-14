package com.employee.attendance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "EmployeeAttendanceManagementSystem", version = "1.0.0"), servers = {
		@Server(url = "http://localhost:8080") })

public class EmployeeAttendanceManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeAttendanceManagementSystemApplication.class, args);
	}

}
