package com.example.demo.services;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.entities.EmployeeEntity;
import com.example.demo.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        if(employeeEntity == null)return null;
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployee() {
        List<EmployeeEntity> employeeEntityList = employeeRepository.findAll();

        return employeeEntityList
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createEmployee(EmployeeDTO employee) {
        EmployeeEntity employeeEntity = employeeRepository.save(modelMapper.map(employee, EmployeeEntity.class));
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employee) {
        boolean isPresent = employeeRepository.existsById(employeeId);
        if(!isPresent)return null;
        EmployeeEntity employeeEntity = modelMapper.map(employee, EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

    public boolean isEmployeeExists(Long employeeId){
        return employeeRepository.existsById(employeeId);
    }

    public Boolean deleteById(Long employeeId) {
        boolean isPresent = isEmployeeExists(employeeId);
        if(!isPresent)return false;
        employeeRepository.deleteById(employeeId);
        return true;
    }

    public EmployeeDTO updateEmployee(Long employeeId, Map<String, Object> updates) {
        boolean isPresent = isEmployeeExists(employeeId);
        if(!isPresent)return null;
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();

        updates.forEach((field, value) -> {
            Field fieldToUpdated = ReflectionUtils.findField(EmployeeEntity.class, field);

            if(fieldToUpdated != null){
                fieldToUpdated.setAccessible(true);
                ReflectionUtils.setField(fieldToUpdated, employeeEntity, value);
            }
        });

        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }
}
