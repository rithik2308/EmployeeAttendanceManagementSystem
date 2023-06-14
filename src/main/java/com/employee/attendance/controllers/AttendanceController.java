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

import com.employee.attendance.entities.Attendance;
import com.employee.attendance.jdbcrepositories.JdbcAttendanceRepository;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
	@Autowired
	JdbcAttendanceRepository attendancerep;

	@GetMapping("/welcome")
	public @ResponseBody String HelloWorld() {
		return "Welcome";
	}

	@GetMapping("/getall")
	public ResponseEntity<List<Attendance>> getAllAttendances() {
		List<Attendance> attendance = attendancerep.findAll();
		return ResponseEntity.ok(attendance);
	}

	@GetMapping("/get/{sr_no}")
	public ResponseEntity<Attendance> getAttendanceById(@PathVariable("sr_no") long sr_no) {
		Attendance attendance = attendancerep.findById(sr_no);

		if (attendance != null) {
			return new ResponseEntity<>(attendance, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getbyempid/{employee_id}")
	public ResponseEntity<List<Attendance>> getAttendanceByEmpId(@PathVariable("employee_id") long employee_id) {
		List<Attendance> attendance = attendancerep.findByEmployeeId(employee_id);
		return ResponseEntity.ok(attendance);
	}

	@GetMapping("/getbymonth/{month}")
	public ResponseEntity<List<Attendance>> getAttendanceByMonth(@PathVariable("month") String month) {
		List<Attendance> attendance = attendancerep.findByMonth(month);
		return ResponseEntity.ok(attendance);
	}

	@PostMapping("/add")
	public ResponseEntity<String> AddAttendance(@RequestBody Attendance attendance) {
		attendancerep.save(new Attendance(attendance.getSr_no(), attendance.getEmployee_id(), attendance.getMonth(),
				attendance.getDate(), attendance.getPresent()));
		return new ResponseEntity<>("Attendance created successfully.", HttpStatus.CREATED);
	}

	@PutMapping("/updatebysrno/{sr_no}")
	public ResponseEntity<String> updateAttendance(@PathVariable("sr_no") long sr_no,
			@RequestBody Attendance attendance) {
		Attendance _attendance = attendancerep.findById(sr_no);

		if (_attendance != null) {
			_attendance.setSr_no(attendance.getSr_no());
			_attendance.setEmployee_id(attendance.getEmployee_id());
			_attendance.setMonth(attendance.getMonth());
			_attendance.setDate(attendance.getDate());
			_attendance.setPresent(attendance.getPresent());
			attendancerep.update(_attendance);
			return new ResponseEntity<>("Attendance updated successfully.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Cannot find attendance with id=" + sr_no, HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{sr_no}")
	public ResponseEntity<String> deleteAttendanceById(@PathVariable("sr_no") long sr_no) {
		boolean result = attendancerep.deleteById(sr_no);
		if (result) {
			return new ResponseEntity<>("Attendance was deleted successfully.", HttpStatus.OK);
		}
		return new ResponseEntity<>("Cannot find attendance with id= " + sr_no, HttpStatus.OK);
	}

	@DeleteMapping("/deletebymonth/{month}")
	public ResponseEntity<String> deleteAttendanceByMonth(@PathVariable("month") String month) {
		boolean result = attendancerep.deleteByMonth(month);
		if (result) {
			return new ResponseEntity<>("Attendance was deleted successfully.", HttpStatus.OK);
		}
		return new ResponseEntity<>("Cannot find attendance with month= " + month, HttpStatus.OK);
	}

}