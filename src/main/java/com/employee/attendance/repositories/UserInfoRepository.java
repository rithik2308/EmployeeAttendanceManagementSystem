package com.employee.attendance.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.attendance.entities.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

	Optional<UserInfo> findByName(String username);

}
