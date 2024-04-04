package com.sayu.employee_management_system.service;

import com.sayu.employee_management_system.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {
    ResponseEntity<Integer> getAllEmployeesByBloodGroup(String bloodGroup);

    ResponseEntity<EmployeeDto> createEmployee(EmployeeDto employeeDto);

    ResponseEntity<EmployeeDto> updateEmployee(Long employeeId, EmployeeDto employeeDto);

    ResponseEntity<?> deleteEmployee(Long id);

    ResponseEntity<EmployeeDto> getEmployeeById(Long employeeId);
    ResponseEntity<List<EmployeeDto>> getAllEmployees();

    ResponseEntity<List<EmployeeDto>> getAllEmployeesByexperianceMoreThan(int experiance);

    ResponseEntity<Integer> getAllEmployeesByIsMarried(boolean yes);

    ResponseEntity<List<EmployeeDto>> getAllEmployeesByAgeAndSalaryGreaterThanAverageSalary(int age);

    ResponseEntity<Double> getAllEmployeesAverageSalary();
}
