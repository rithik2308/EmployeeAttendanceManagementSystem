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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.employee.attendance.entities.Attendance;
import com.employee.attendance.jdbcrepositories.JdbcAttendanceRepository;

@SpringBootTest(classes = JdbcAttendanceRepositoryMockito.class)
public class JdbcAttendanceRepositoryMockito {

	@Mock
	private JdbcTemplate jdbctemplate;

	@InjectMocks
	private JdbcAttendanceRepository jdbcattendancerep;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSave() {
		Attendance attendance = new Attendance(1, 1, "jan", 1, "yes");
		jdbcattendancerep.save(attendance);
		verify(jdbctemplate, times(1)).update(anyString(), eq((long) 1), eq((long) 1), eq("jan"), eq(1), eq("yes"));
	}

	@Test
	public void testFindAll() {
		List<Attendance> attendance = new ArrayList<>();
		attendance.add(new Attendance(1, 1, "jan", 1, "yes"));
		attendance.add(new Attendance(1, 1, "feb", 1, "yes"));
		when(jdbctemplate.query(anyString(), any(BeanPropertyRowMapper.class))).thenReturn(attendance);
		List<Attendance> result = jdbcattendancerep.findAll();
		verify(jdbctemplate, times(1)).query(anyString(), any(BeanPropertyRowMapper.class));
		assertEquals(2, result.size());
	}

	@Test
	public void testDeleteBySrNo() {
		jdbcattendancerep.deleteById((long) 1);
		verify(jdbctemplate, times(1)).update(anyString(), any(Long.class));
	}

	@Test
	public void testDeleteByMonth() {
		jdbcattendancerep.deleteByMonth("jan");
		verify(jdbctemplate, times(1)).update(anyString(), anyString());
	}

	@Test
	public void tesFindbyEmployeeid() {
		List<Attendance> attendance = new ArrayList<>();
		attendance.add(new Attendance(1, 1, "jan", 1, "yes"));
		attendance.add(new Attendance(1, 1, "feb", 1, "yes"));
		when(jdbctemplate.query(anyString(), any(BeanPropertyRowMapper.class))).thenReturn(attendance);
		List<Attendance> result = jdbcattendancerep.findByEmployeeId((long) 1);
		verify(jdbctemplate, times(1)).query(anyString(), any(BeanPropertyRowMapper.class), any((Long.class)));
	}

	@Test
	public void tesFindbyMonth() {
		List<Attendance> attendance = new ArrayList<>();
		attendance.add(new Attendance(1, 1, "jan", 1, "yes"));
		attendance.add(new Attendance(1, 1, "feb", 1, "yes"));
		when(jdbctemplate.query(anyString(), any(BeanPropertyRowMapper.class))).thenReturn(attendance);
		List<Attendance> result = jdbcattendancerep.findByMonth("jan");
		verify(jdbctemplate, times(1)).query(anyString(), any(BeanPropertyRowMapper.class), anyString());
//        assertEquals(1, result.size());
	}

	@Test
	public void tesFindbySrNo() {
		Attendance attendance = new Attendance(1, 1, "jan", 1, "yes");
		when(jdbctemplate.queryForObject(anyString(), any(BeanPropertyRowMapper.class), any(Long.class)))
				.thenReturn(attendance);
		Attendance result = jdbcattendancerep.findById((long) 1);
		verify(jdbctemplate, times(1)).queryForObject(anyString(), any(BeanPropertyRowMapper.class), any(Long.class));
		assertEquals(1, result.getEmployee_id());
	}

	@Test
	public void testUpdateEmployee() {
		// Create a new employee object with updated information
		Attendance attendance = new Attendance(1, 1, "jan", 1, "yes");

		// Mock the behavior of JdbcTemplate update method
		when(jdbctemplate.update(anyString(), any(Object[].class))).thenReturn(1); // Indicate that one row was updated

		// Call the repository method to update the employee
		boolean updated = jdbcattendancerep.update(attendance);

		// Verify the interaction with JdbcTemplate and assert the expected results
		verify(jdbctemplate, times(1)).update(anyString(), any(Object[].class));
		assertTrue(updated);
	}
}
