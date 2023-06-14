package com.employee.attendance.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.attendance.entities.Employee;
import com.employee.attendance.jdbcrepositories.JdbcEmployeeRepository;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	JdbcEmployeeRepository employeerep;

	@GetMapping("/welcome")
	public @ResponseBody String HelloWorld() {
		return "Welcome";
	}

	@GetMapping("/getall")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> employees = employeerep.findAll();
		return ResponseEntity.ok(employees);
	}

	@GetMapping("/getbyid/{employee_id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("employee_id") long employee_id) {
		Employee employee = employeerep.findById(employee_id);

		if (employee != null) {
			return new ResponseEntity<>(employee, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getbyname/{name}")
	public ResponseEntity<Employee> getEmployeeByName(@PathVariable("name") String name) {
		Employee employee = employeerep.findByName(name);

		if (employee != null) {
			return new ResponseEntity<>(employee, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<String> AddEmployee(@RequestBody Employee employee) {
		employeerep.save(new Employee(employee.getEmployee_id(), employee.getName(), employee.getEmail(),
				employee.getAddress()));
		return new ResponseEntity<>("Employee created successfully.", HttpStatus.CREATED);

	}

	@PutMapping("/updatebyid/{employee_id}")
	public ResponseEntity<String> updateEmployee(@PathVariable("employee_id") long employee_id,
			@RequestBody Employee employee) {
		Employee _employee = employeerep.findById(employee_id);

		if (_employee != null) {
			_employee.setEmployee_id(employee_id);
			_employee.setName(employee.getName());
			_employee.setEmail(employee.getEmail());
			_employee.setAddress(employee.getAddress());

			employeerep.update(_employee);
			return new ResponseEntity<>("Employee updated successfully.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Cannot find employee with id=" + employee_id, HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{employee_id}")
	public ResponseEntity<String> deleteEmployeeById(@PathVariable("employee_id") long employee_id) {
		boolean result = employeerep.deleteById(employee_id);
		if (result) {
			return new ResponseEntity<>("Employee was deleted successfully.", HttpStatus.OK);
		}
		return new ResponseEntity<>("Cannot find employee with id= " + employee_id, HttpStatus.OK);

	}

	@DeleteMapping("/deletebyname/{name}")
	public ResponseEntity<String> deleteEmployeeByName(@PathVariable("name") String name) {
		boolean result = employeerep.deleteByName(name);
		if (result) {
			return new ResponseEntity<>("Employee was deleted successfully.", HttpStatus.OK);
		}
		return new ResponseEntity<>("Cannot find employee with name= " + name, HttpStatus.OK);

	}
}
