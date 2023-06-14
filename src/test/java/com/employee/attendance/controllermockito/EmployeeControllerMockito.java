package com.employee.attendance.controllermockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.employee.attendance.controllers.EmployeeController;
import com.employee.attendance.jdbcrepositories.JdbcEmployeeRepository;
import com.employee.attendance.entities.Employee;

@SpringBootTest(classes = EmployeeControllerMockito.class)
public class EmployeeControllerMockito {
	@Mock
	private JdbcEmployeeRepository jdbcemployeerep;

	@InjectMocks
	private EmployeeController employeecontroller;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testHelloWorld() {
		assertEquals("Welcome", employeecontroller.HelloWorld());
	}

	@Test
	public void testGetAll() {
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee(1, "John", "john@gmail.com", "Boston"));
		employees.add(new Employee(2, "Jane", "jane@gmail.com", "Texas"));

		when(jdbcemployeerep.findAll()).thenReturn(employees);
		ResponseEntity<List<Employee>> response = employeecontroller.getAllEmployees();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
		assertTrue(response.getBody().contains(employees.get(0)));
		assertTrue(response.getBody().contains(employees.get(1)));

		verify(jdbcemployeerep, times(1)).findAll();
	}

	@Test
	public void testAddEmploye() {
		String message = "Employee created successfully.";
		HttpStatus status = HttpStatus.CREATED;
		Employee employee = new Employee(1, "John", "john@gmail.com", "Boston");
		employeecontroller.AddEmployee(employee);

		ResponseEntity<String> responseEntity = new ResponseEntity<>(message, status);

		assertEquals(message, responseEntity.getBody());
		assertEquals(status, responseEntity.getStatusCode());
	}

	@Test
	public void testUpdateEmployee() {
		String message = "Employee updated successfully.";
		HttpStatus status = HttpStatus.OK;
		Employee employee = new Employee(1, "John", "john@gmail.com", "Boston");
		employeecontroller.updateEmployee(1, employee);
		ResponseEntity<String> responseEntity = new ResponseEntity<>(message, status);

		assertEquals(message, responseEntity.getBody());
		assertEquals(status, responseEntity.getStatusCode());

	}

	@Test
	public void testGetEmployeeById_ExistingEmployee() {
		Employee employee = new Employee(1, "John", "john@gmail.com", "Boston");

		when(jdbcemployeerep.findById((long) 1)).thenReturn(employee);

		ResponseEntity<Employee> response = employeecontroller.getEmployeeById(1);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(employee, response.getBody());

		verify(jdbcemployeerep, times(1)).findById((long) 1);
	}

	@Test
    public void testGetEmployeeById_NonExistingEmployee() {
        when(jdbcemployeerep.findById((long) 1)).thenReturn(null);

        ResponseEntity<Employee> response = employeecontroller.getEmployeeById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(jdbcemployeerep, times(1)).findById((long) 1);
    }

	@Test
	public void testGetEmployeeByName_ExistingEmployee() {
		Employee employee = new Employee(1, "John", "john@gmail.com", "Boston");

		when(jdbcemployeerep.findByName("John")).thenReturn(employee);

		ResponseEntity<Employee> response = employeecontroller.getEmployeeByName("John");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(employee, response.getBody());

		verify(jdbcemployeerep, times(1)).findByName("John");
	}

	@Test
    public void testGetEmployeeByName_NonExistingEmployee() {
        when(jdbcemployeerep.findById((long) 1)).thenReturn(null);

        ResponseEntity<Employee> response = employeecontroller.getEmployeeByName("John");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(jdbcemployeerep, times(1)).findByName("John");
    }

	@Test
    public void testDeleteEmployeeById_ExistingEmployee() {
        when(jdbcemployeerep.deleteById((long) 1)).thenReturn(true);

        ResponseEntity<String> response = employeecontroller.deleteEmployeeById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee was deleted successfully.",response.getBody());

        verify(jdbcemployeerep, times(1)).deleteById((long) 1);
    }

	@Test
    public void testDeleteEmployeeById_NonExistingEmployee() {
    	when(jdbcemployeerep.deleteById((long) 1)).thenReturn(false);

        ResponseEntity<String> response = employeecontroller.deleteEmployeeById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cannot find employee with id= " + 1,response.getBody());

        verify(jdbcemployeerep, times(1)).deleteById((long) 1);
    }

	@Test
    public void testDeleteEmployeeByName_ExistingEmployee() {
        when(jdbcemployeerep.deleteByName("John")).thenReturn(true);

        ResponseEntity<String> response = employeecontroller.deleteEmployeeByName("John");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee was deleted successfully.",response.getBody());

        verify(jdbcemployeerep, times(1)).deleteByName("John");
    }

	@Test
    public void testDeleteEmployeeByName_NonExistingEmployee() {
    	when(jdbcemployeerep.deleteByName("John")).thenReturn(false);

        ResponseEntity<String> response = employeecontroller.deleteEmployeeByName("John");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cannot find employee with name= " + "John",response.getBody());

        verify(jdbcemployeerep, times(1)).deleteByName("John");
    }

}
