package com.employee;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author 001810240/TaufanSeptaufani
 * Employee Controller with action : get employees, employee by id, add new employee, replace employee, delete employee
 * Please restart the application if there is new data from input manually in table employee,
 * because PostMapping will error:duplicat entry for id while add new employee
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
	
	@PostMapping(path = "/employees")
	public @ResponseBody ResponseEntity<?> add(@RequestBody Employee NewEmployee){
		EntityModel<Employee> entityModel = assembler.toModel(repository.save(NewEmployee)); //save new employee
		
		return ResponseEntity //is used to create an HTTP 201 Created status message, includes a Location response header
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //and use the URI derived from the model’s self-related link
				.body(entityModel);
	}
	
	@PutMapping(path = "/employees/{id}")
	public @ResponseBody ResponseEntity<?> replace(@RequestBody Employee NewEmployee,@PathVariable(value = "id") Long id){
		Employee updateEmployee = repository.findById(id) //find employee existing by id
				.map(employee ->{ // and then
					employee.setFirstname(NewEmployee.getFirstname()); //set FirstName
					employee.setLastname(NewEmployee.getLastname()); //set LastName
					employee.setRole(NewEmployee.getRole());//set Role
					return repository.save(employee); // and save
				})
				.orElseGet(()->{ //or if not found the existing
					NewEmployee.setId(id); // then set id
					return repository.save(NewEmployee); // and save as a new employee (not replace the existing)
				});
		
		EntityModel<Employee> entityModel = assembler.toModel(updateEmployee); //employee with link creation from assembler to model
		
		return ResponseEntity //is used to create an HTTP 201 Created status message, includes a Location response header
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //and use the URI derived from the model’s self-related link
				.body(entityModel);
	}
	
	@DeleteMapping(path = "/employees/{id}")
	public @ResponseBody ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
		repository.deleteById(id); //delete employee by id
		
		return ResponseEntity.status(HttpStatus.OK).build(); //returning response HTTP 200 OK
	}
}
