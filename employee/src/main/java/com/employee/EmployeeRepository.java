package com.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author 001810240/TaufanSeptaufani
 * JpaRepository will automatically handling for get, post, put, delete in controller
 * Don't need create query to interact with database repository
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
}
