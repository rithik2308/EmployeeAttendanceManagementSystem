package com.employee.attendance.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.employee.attendance.entities.Employee;

@Repository
public interface EmployeeRepository {

	int save(Employee employee);

	boolean update(Employee employee);

	Employee findById(Long employee_id);

	Employee findByName(String name);

	boolean deleteById(Long employee_id);

	boolean deleteByName(String name);

	List<Employee> findAll();

}
