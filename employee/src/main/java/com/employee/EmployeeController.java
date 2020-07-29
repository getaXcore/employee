package com.employee;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author 001810240/TaufanSeptaufani
 * Employee Controller with action : get all employee, employee by id
 *
 */
@RestController
class EmployeeController {

	@Autowired
	private final EmployeeRepository repository;
	private final EmployeeModelAssembler assembler;
	
	public EmployeeController(EmployeeRepository repository,EmployeeModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}
	
	@GetMapping(path = "/employees")
	public @ResponseBody CollectionModel<EntityModel<Employee>> findAll(){
		
		//employees into list and then collected. Assembler used for link creation  for each employee in a list
		List<EntityModel<Employee>> employees = repository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		
		//All wrapped in big Collection, and add link creation for employees
		return CollectionModel.of(employees,
				linkTo(methodOn(EmployeeController.class).findAll()).withSelfRel());
	}
	
	@GetMapping(path = "/employees/{id}")
	public @ResponseBody EntityModel<Employee> findById(@PathVariable(value = "id") Long id){
		Employee employee = repository.findById(id) //find employee by id
				.orElseThrow(()->new EmployeeNotFoundException(id)); //returning message if employee with some id not found
		
		//returning employee with link creation from assembler
		return assembler.toModel(employee);
	}
}
