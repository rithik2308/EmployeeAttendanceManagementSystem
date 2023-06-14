package com.employee.attendance.controllermockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.employee.attendance.controllers.AttendanceController;
import com.employee.attendance.entities.Attendance;
import com.employee.attendance.entities.Employee;
import com.employee.attendance.jdbcrepositories.JdbcAttendanceRepository;

@SpringBootTest(classes = AttendanceControllerMockito.class)
public class AttendanceControllerMockito {
	@Mock
	private JdbcAttendanceRepository jdbcattendancerep;

	@InjectMocks
	private AttendanceController attendancecontroller;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testHelloWorld() {
		assertEquals("Welcome", attendancecontroller.HelloWorld());
	}

	@Test
	public void testGetAll() {
		List<Attendance> attendance = new ArrayList<>();
		attendance.add(new Attendance(1, 1, "jan", 1, "yes"));
		attendance.add(new Attendance(2, 1, "jan", 2, "yes"));

		when(jdbcattendancerep.findAll()).thenReturn(attendance);
		ResponseEntity<List<Attendance>> response = attendancecontroller.getAllAttendances();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
		assertTrue(response.getBody().contains(attendance.get(0)));
		assertTrue(response.getBody().contains(attendance.get(1)));

		verify(jdbcattendancerep, times(1)).findAll();
	}

	@Test
	public void testGetByEmployeeId() {
		List<Attendance> attendance = new ArrayList<>();
		attendance.add(new Attendance(1, 1, "jan", 1, "yes"));
		attendance.add(new Attendance(2, 1, "jan", 2, "yes"));

		when(jdbcattendancerep.findByEmployeeId((long) 1)).thenReturn(attendance);
		ResponseEntity<List<Attendance>> response = attendancecontroller.getAttendanceByEmpId(1);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
		assertTrue(response.getBody().contains(attendance.get(0)));
		assertTrue(response.getBody().contains(attendance.get(1)));

		verify(jdbcattendancerep, times(1)).findByEmployeeId((long) 1);
	}

	@Test
	public void testGetByMonth() {
		List<Attendance> attendance = new ArrayList<>();
		attendance.add(new Attendance(1, 1, "jan", 1, "yes"));
		attendance.add(new Attendance(2, 1, "jan", 2, "yes"));

		when(jdbcattendancerep.findByMonth("jan")).thenReturn(attendance);
		ResponseEntity<List<Attendance>> response = attendancecontroller.getAttendanceByMonth("jan");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
		assertTrue(response.getBody().contains(attendance.get(0)));
		assertTrue(response.getBody().contains(attendance.get(1)));

		verify(jdbcattendancerep, times(1)).findByMonth("jan");
	}

	@Test
	public void testAddEmploye() {
		String message = "Attendance created successfully.";
		HttpStatus status = HttpStatus.CREATED;
		Attendance attendance = new Attendance(2, 1, "jan", 2, "yes");
		attendancecontroller.AddAttendance(attendance);

		ResponseEntity<String> responseEntity = new ResponseEntity<>(message, status);

		assertEquals(message, responseEntity.getBody());
		assertEquals(status, responseEntity.getStatusCode());
	}

	@Test
	public void testUpdateAttendance() {
		String message = "Attendance updated successfully.";
		HttpStatus status = HttpStatus.OK;
		Attendance attendance = new Attendance(2, 1, "jan", 2, "yes");
		attendancecontroller.updateAttendance(1, attendance);
		ResponseEntity<String> responseEntity = new ResponseEntity<>(message, status);

		assertEquals(message, responseEntity.getBody());
		assertEquals(status, responseEntity.getStatusCode());

	}

	@Test
	public void testGetAttendanceById_ExistingEmployee() {
		Attendance attendance = new Attendance(2, 1, "jan", 2, "yes");

		when(jdbcattendancerep.findById((long) 1)).thenReturn(attendance);

		ResponseEntity<Attendance> response = attendancecontroller.getAttendanceById(1);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(attendance, response.getBody());

		verify(jdbcattendancerep, times(1)).findById((long) 1);
	}

	@Test
    public void testGetAttendanceById_NonExistingEmployee() {
        when(jdbcattendancerep.findById((long) 1)).thenReturn(null);

        ResponseEntity<Attendance> response = attendancecontroller.getAttendanceById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(jdbcattendancerep, times(1)).findById((long) 1);
    }

	@Test
    public void testDeleteAttendanceById_ExistingEmployee() {
        when(jdbcattendancerep.deleteById((long) 1)).thenReturn(true);

        ResponseEntity<String> response = attendancecontroller.deleteAttendanceById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Attendance was deleted successfully.",response.getBody());

        verify(jdbcattendancerep, times(1)).deleteById((long) 1);
    }

	@Test
    public void testDeleteAttendanceById_NonExistingEmployee() {
    	when(jdbcattendancerep.deleteById((long) 1)).thenReturn(false);

        ResponseEntity<String> response = attendancecontroller.deleteAttendanceById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cannot find attendance with id= " + 1,response.getBody());

        verify(jdbcattendancerep, times(1)).deleteById((long) 1);
    }

	@Test
    public void testDeleteAttendanceByMonth_ExistingEmployee() {
        when(jdbcattendancerep.deleteByMonth("jan")).thenReturn(true);

        ResponseEntity<String> response = attendancecontroller.deleteAttendanceByMonth("jan");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Attendance was deleted successfully.",response.getBody());

        verify(jdbcattendancerep, times(1)).deleteByMonth("jan");
    }

	@Test
    public void testDeleteAttendanceByMonth_NonExistingEmployee() {
    	when(jdbcattendancerep.deleteByMonth("Jan")).thenReturn(false);

        ResponseEntity<String> response = attendancecontroller.deleteAttendanceByMonth("jan");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cannot find attendance with month= " + "jan",response.getBody());

        verify(jdbcattendancerep, times(1)).deleteByMonth("jan");
    }

}
