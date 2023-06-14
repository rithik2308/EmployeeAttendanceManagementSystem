package com.employee.attendance.jdbcrepositories;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.employee.attendance.entities.Employee;
import com.employee.attendance.repositories.EmployeeRepository;

@Service
public class JdbcEmployeeRepository implements EmployeeRepository {
	private static final Logger LOGGER = LoggerFactory.getLogger(JdbcEmployeeRepository.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int save(Employee employee) {
		LOGGER.info("Adding an employee to the database");
		String query = "INSERT INTO employee (employee_id,name,email,address) VALUES(?,?,?,?)";
		return jdbcTemplate.update(query, new Object[] { employee.getEmployee_id(), employee.getName(),
				employee.getEmail(), employee.getAddress() });
	}

	@Override
	public boolean update(Employee employee) {
		LOGGER.info("Updating employee details in the database");
		String query = "UPDATE employee SET name=?, email=?, address=? WHERE employee_id=?";
		int rows_updated = jdbcTemplate.update(query, new Object[] { employee.getName(), employee.getEmail(),
				employee.getAddress(), employee.getEmployee_id() });
		return rows_updated > 0;
	}

	@Override
	public Employee findById(Long employee_id) {
		try {
			LOGGER.info("Getting employee details in the database using employee_id: " + employee_id);
			Employee employee = jdbcTemplate.queryForObject("SELECT * FROM employee WHERE employee_id=?",
					BeanPropertyRowMapper.newInstance(Employee.class), employee_id);

			return employee;
		} catch (IncorrectResultSizeDataAccessException e) {
			LOGGER.error("Employee details with employee_id: " + employee_id + " not found");
			return null;
		}
	}

	@Override
	public boolean deleteById(Long employee_id) {
		LOGGER.info("Deleting employee details in the database using employee_id: " + employee_id);
		String query = "DELETE FROM employee WHERE employee_id=?";
		jdbcTemplate.update(query, employee_id);
		return true;
	}

	@Override
	public boolean deleteByName(String name) {
		LOGGER.info("Deleting employee details in the database using employee name: " + name);
		String query = "DELETE FROM employee WHERE name=?";
		jdbcTemplate.update(query, name);
		return true;
	}

	@Override
	public List<Employee> findAll() {
		LOGGER.info("Fetching all employee details in the database");
		String query = "SELECT * from employee";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Employee.class));
	}

	@Override
	public Employee findByName(String name) {
		try {
			LOGGER.info("Getting employee details in database with employee name: " + name);
			Employee employee = jdbcTemplate.queryForObject("SELECT * FROM employee WHERE name=?",
					BeanPropertyRowMapper.newInstance(Employee.class), name);

			return employee;
		} catch (IncorrectResultSizeDataAccessException e) {
			LOGGER.error("Employee details not fetched");
			return null;
		}
	}

}
