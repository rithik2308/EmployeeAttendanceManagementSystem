package com.employee.attendance.jdbcrepositories;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.employee.attendance.entities.Attendance;
import com.employee.attendance.repositories.AttendanceRepository;

@Service
public class JdbcAttendanceRepository implements AttendanceRepository {
	private static final Logger LOGGER = LoggerFactory.getLogger(JdbcAttendanceRepository.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int save(Attendance attendance) {
		LOGGER.info("Adding attendance to the database");
		String query = "INSERT INTO attendance(sr_no, employee_id, month, date, present) VALUES(?,?,?,?,?)";
		return jdbcTemplate.update(query, new Object[] { attendance.getSr_no(), attendance.getEmployee_id(),
				attendance.getMonth(), attendance.getDate(), attendance.getPresent() });
	}

	@Override
	public boolean update(Attendance attendance) {
		LOGGER.info("Updating attendance in the database");
		String query = "UPDATE attendance SET employee_id=?, month=?, date=?, present=? WHERE sr_no=?";
		int rows_updated = jdbcTemplate.update(query, new Object[] { attendance.getEmployee_id(), attendance.getMonth(),
				attendance.getDate(), attendance.getPresent(), attendance.getSr_no() });
		return rows_updated > 0;
	}

	@Override
	public Attendance findById(Long sr_no) {
		try {
			LOGGER.info("Getting attendance in the database using sr_no: " + sr_no);
			String query = "SELECT * FROM attendance WHERE sr_no=?";
			Attendance attendance = jdbcTemplate.queryForObject(query,
					BeanPropertyRowMapper.newInstance(Attendance.class), sr_no);
			return attendance;
		} catch (IncorrectResultSizeDataAccessException e) {
			LOGGER.error("Attendance details not fetched for sr_no: " + sr_no);
			return null;
		}
	}

	@Override
	public List<Attendance> findByEmployeeId(Long employee_id) {
		try {
			LOGGER.info("Getting attendance in the database using employee_id: " + employee_id);
			String query = "SELECT * from attendance WHERE employee_id=?";
			return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Attendance.class), employee_id);
		} catch (IncorrectResultSizeDataAccessException e) {
			LOGGER.error("Attendance details not fetched for employee_id: " + employee_id);
			return null;
		}
	}

	@Override
	public boolean deleteById(Long sr_no) {
		LOGGER.info("Deleting attendance in the database with sr_no: " + sr_no);
		String query = "DELETE FROM attendance WHERE sr_no=?";
		jdbcTemplate.update(query, sr_no);
		return true;
	}

	@Override
	public boolean deleteByMonth(String month) {
		LOGGER.info("Deleting attendance in the database with month: " + month);
		String query = "DELETE FROM attendance WHERE month=?";
		jdbcTemplate.update(query, month);
		return true;
	}

	@Override
	public List<Attendance> findByMonth(String month) {
		try {
			LOGGER.info("Getting attendance in the database for month: " + month);
			String query = "SELECT * from attendance WHERE month=?";
			return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Attendance.class), month);
		} catch (IncorrectResultSizeDataAccessException e) {
			LOGGER.error("Attendance details not fetched for month: " + month);
			return null;
		}
	}

	@Override
	public List<Attendance> findAll() {
		LOGGER.info("Getting all attendance in the database");
		String query = "SELECT * from attendance";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Attendance.class));
	}

}
