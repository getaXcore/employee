package com.employee;

/**
 * 
 * @author 001810240/TaufanSeptaufani
 * This is for returning message if employee not found, but just show in console
 *
 */
public class EmployeeNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeNotFoundException(Long id) {
		super("Not Found employee with id "+id);
	}
}
