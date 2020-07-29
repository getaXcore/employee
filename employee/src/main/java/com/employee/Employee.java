package com.employee;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author 001810240/TaufanSeptaufani
 * Entity of Employee for interact with
 *
 */
@Entity
@Table(name = "Employee")
public class Employee {
	private @Id @GeneratedValue Long id; //same as AUTO_INCREMENT
	private String firstname;
	private String lastname;
	private String role;
	
	public Employee() {
		// TODO Auto-generated constructor stub
	}
	
	public Employee(String firstname,String lastname,String role) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.role = role;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getFirstname() {
		return this.firstname;
	}
	
	public String getLastname() {
		return this.lastname;
	}
	
	public String getRole() {
		return this.role;
	}
}
