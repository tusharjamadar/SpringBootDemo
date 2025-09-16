package com.example.demo.controllers;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee API", description = "Operations related to Employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/{employeeId}")
    @Operation(summary = "Get employee by ID", description = "Returns user details by given ID")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId") Long Id){
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(Id);
        if(employeeDTO == null)return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping
    @Operation(summary = "Sort employee by ID", description = "Returns user details by given ID")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee(@RequestParam(required = false, name = "age") Integer inputAge, @RequestParam(required = false, name = "sortBy") String sortBy ){
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @PostMapping
    @Operation(summary = "Create new Employee", description = "This is test api used for creating new Employee")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employee){
        EmployeeDTO employeeDTO = employeeService.createEmployee(employee);
        return new ResponseEntity<>(employeeDTO, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{employeeId}")
    @Operation(summary = "Update Employee", description = "This API used to update the employee")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody EmployeeDTO employee, @PathVariable Long employeeId){
        EmployeeDTO employeeDTO = employeeService.updateEmployeeById(employeeId, employee);
        if(employeeDTO == null)return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }

    @DeleteMapping(path = "/{employeeId}")
    @Operation(summary = "Delete Employee", description = "This API used to delete the employee")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long employeeId){
        Boolean res = employeeService.deleteById(employeeId);
        if(res)return ResponseEntity.ok(res);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{employeeId}")
    @Operation(summary = "Udpate Field of Employee", description = "This API used to Udpate Field of Employee")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody Map<String, Object> updates, @PathVariable Long employeeId){
        EmployeeDTO employeeDTO = employeeService.updateEmployee(employeeId, updates);
        if(employeeDTO == null)return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }
}
