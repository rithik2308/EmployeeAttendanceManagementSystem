package com.employee.attendance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
	private long sr_no;
	private long employee_id;
	private String month;
	private int date;
	private String present;
}
