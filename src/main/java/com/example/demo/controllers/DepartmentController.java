package com.example.demo.controllers;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.services.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@Tag(name = "Department API", description = "Operations related to department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    @Operation(description = "Get All Departments")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(
            @RequestParam(required = false, name = "sortBy") String sortBy) {
        List<DepartmentDTO> departmentDTO = departmentService.getAllDepartments();
        return ResponseEntity.ok(departmentDTO);
    }

    @GetMapping("/{departmentId}")
    @Operation(description = "Get Department by ID")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long departmentId) {
        DepartmentDTO departmentDTO = departmentService.getDepartmentById(departmentId);
        if (departmentDTO == null) {
            throw new ResourceNotFoundException("Department with ID " + departmentId + " not found.");
        }
        return ResponseEntity.ok(departmentDTO);
    }

    @PostMapping
    @Operation(description = "Create new Department")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO createdDepartment = departmentService.createDepartment(departmentDTO);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    @PutMapping("/{departmentId}")
    @Operation(description = "Update the Department by ID")
    public ResponseEntity<DepartmentDTO> updateDepartmentById(
            @RequestBody DepartmentDTO departmentDTO,
            @PathVariable Long departmentId) {
        DepartmentDTO updatedDepartment = departmentService.updateDepartmentById(departmentId, departmentDTO);
        if (updatedDepartment == null) {
            throw new ResourceNotFoundException("Department with ID " + departmentId + " not found.");
        }
        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping("/{departmentId}")
    @Operation(summary = "Delete Department")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long departmentId) {
        Boolean res = departmentService.deleteById(departmentId);
        if (res) return ResponseEntity.ok(res);
        else throw new ResourceNotFoundException("Department with ID " + departmentId + " not found.");
    }
}
