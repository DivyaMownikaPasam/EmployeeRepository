package com.employee.management.controller;


import com.employee.management.exception.ResourceNotFoundException;
import com.employee.management.model.Employee;
import com.employee.management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class EmployeeController {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
	    return employeeRepository.findAll();
	}
	
	@PostMapping("/employees")
	public Employee createEmployee(@Valid @RequestBody Employee employee) {
	    return employeeRepository.save(employee);
	}
	
	@GetMapping("/employees/{id}")
	public Employee getEmployeeById(@PathVariable(value = "id") Long employeeId) {
	    return employeeRepository.findById(employeeId)
	            .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
	}
	
	@PutMapping("/employees/{id}")
	public Employee updatedEmployee(@PathVariable(value = "id") Long employeeId,
	                                        @Valid @RequestBody Employee employeeDetails) {

		Employee employee = employeeRepository.findById(employeeId)
	            .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

		employee.setName(employeeDetails.getName());
		employee.setJob(employeeDetails.getJob());
		employee.setSsn(employeeDetails.getSsn());

	    Employee updatedEmployee = employeeRepository.save(employee);
	    return updatedEmployee;
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") Long noteId) {
		Employee employee = employeeRepository.findById(noteId)
	            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

		employeeRepository.delete(employee);

	    return ResponseEntity.ok().build();
	}
}
