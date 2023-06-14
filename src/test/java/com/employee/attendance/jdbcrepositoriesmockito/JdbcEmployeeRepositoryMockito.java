package com.employee.attendance.jdbcrepositoriesmockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.employee.attendance.jdbcrepositories.JdbcEmployeeRepository;
import com.employee.attendance.entities.Employee;

@SpringBootTest(classes = JdbcEmployeeRepositoryMockito.class)
public class JdbcEmployeeRepositoryMockito {
	@Mock
	private JdbcTemplate jdbctemplate;

	@InjectMocks
	private JdbcEmployeeRepository jdbcemployeerep;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSave() {
		// Create a new employee object without setting the ID
		Employee employee = new Employee(1, "John", "john@gmail.com", "Boston");

		// Call the repository method to create an employee
		jdbcemployeerep.save(employee);

		// Verify the interaction with JdbcTemplate and assert the expected results
		verify(jdbctemplate, times(1)).update(anyString(), eq((long) 1), eq("John"), eq("john@gmail.com"),
				eq("Boston"));
	}

	@Test
	public void testFindAll() {
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee(1, "John", "john@gmail.com", "Boston"));
		employees.add(new Employee(2, "Jane", "jane@gmail.com", "Texas"));
		when(jdbctemplate.query(anyString(), any(BeanPropertyRowMapper.class))).thenReturn(employees);
		List<Employee> result = jdbcemployeerep.findAll();
		verify(jdbctemplate, times(1)).query(anyString(), any(BeanPropertyRowMapper.class));
		assertEquals(2, result.size());
	}

	@Test
	public void testDeleteById() {
		// Call the repository method to delete an employee
		jdbcemployeerep.deleteById((long) 1);

		// Verify the interaction with JdbcTemplate
		verify(jdbctemplate, times(1)).update(anyString(), any(Long.class));
	}

	@Test
	public void testDeleteByName() {
		// Call the repository method to delete an employee
		jdbcemployeerep.deleteByName("name");

		// Verify the interaction with JdbcTemplate
		verify(jdbctemplate, times(1)).update(anyString(), anyString());
	}

	@Test
	public void tesFindbyEmployeeid() {
		Employee employee = new Employee(1, "John", "john@gmail.com", "Boston");
		when(jdbctemplate.queryForObject(anyString(), any(BeanPropertyRowMapper.class), any(Long.class)))
				.thenReturn(employee);
		Employee result = jdbcemployeerep.findById((long) 1);
		verify(jdbctemplate, times(1)).queryForObject(anyString(), any(BeanPropertyRowMapper.class), any(Long.class));
		assertEquals("John", result.getName());
	}

	@Test
    public void testFindById_NonExistingEmployee() {
        // Mock the behavior of JdbcTemplate queryForObject method
        when(jdbctemplate.queryForObject(anyString(), any(BeanPropertyRowMapper.class), any(Long.class)))
                .thenReturn(null);

        // Call the repository method to find an employee by ID
        Employee foundEmployee = jdbcemployeerep.findById((long) 2);

        // Verify the interaction with JdbcTemplate and assert the expected results
        verify(jdbctemplate, times(1)).queryForObject(anyString(), any(BeanPropertyRowMapper.class), any(Long.class));
        assertNull(foundEmployee);
    }

	@Test
	public void tesFindbyEmployeeName() {
		Employee employee = new Employee(1, "John", "john@gmail.com", "Boston");
		when(jdbctemplate.queryForObject(anyString(), any(BeanPropertyRowMapper.class), anyString()))
				.thenReturn(employee);
		Employee result = jdbcemployeerep.findByName("John");
		verify(jdbctemplate, times(1)).queryForObject(anyString(), any(BeanPropertyRowMapper.class), anyString());
		assertEquals(1, result.getEmployee_id());
	}

	@Test
    public void testFindByName_NonExistingEmployee() {
        // Mock the behavior of JdbcTemplate queryForObject method
        when(jdbctemplate.queryForObject(anyString(), any(BeanPropertyRowMapper.class), anyString()))
                .thenReturn(null);

        // Call the repository method to find an employee by ID
        Employee foundEmployee = jdbcemployeerep.findByName("Johnny");

        // Verify the interaction with JdbcTemplate and assert the expected results
        verify(jdbctemplate, times(1)).queryForObject(anyString(), any(BeanPropertyRowMapper.class), anyString());
        assertNull(foundEmployee);
    }

	@Test
	public void testUpdateEmployee() {
		// Create a new employee object with updated information
		Employee employee = new Employee(1, "John", "john@gmail.com", "Boston");

		// Mock the behavior of JdbcTemplate update method
		when(jdbctemplate.update(anyString(), any(Object[].class))).thenReturn(1); // Indicate that one row was updated

		// Call the repository method to update the employee
		boolean updated = jdbcemployeerep.update(employee);

		// Verify the interaction with JdbcTemplate and assert the expected results
		verify(jdbctemplate, times(1)).update(anyString(), any(Object[].class));
		assertTrue(updated);
	}

}
