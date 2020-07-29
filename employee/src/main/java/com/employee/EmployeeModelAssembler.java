package com.employee;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 001810240/TaufanSeptaufani
 * Simplifying Link Creation, link will automatically created (as navigation) appeared in response body
 *
 */
@Component
class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {
	
	@Override
	public EntityModel<Employee> toModel(Employee employee){
		return EntityModel.of(employee,
				linkTo(methodOn(EmployeeController.class).findById(employee.getId())).withSelfRel(),
				linkTo(methodOn(EmployeeController.class).findAll()).withRel("employees"));
	}
	
}
