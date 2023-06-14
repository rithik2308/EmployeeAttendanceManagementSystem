package com.employee.attendance.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.employee.attendance.entities.Attendance;

@Repository
public interface AttendanceRepository {

	int save(Attendance attendance);

	boolean update(Attendance attendance);

	Attendance findById(Long sr_no);

	List<Attendance> findByEmployeeId(Long employee_id);

	boolean deleteById(Long sr_no);

	boolean deleteByMonth(String month);

	List<Attendance> findByMonth(String month);

	List<Attendance> findAll();
}
