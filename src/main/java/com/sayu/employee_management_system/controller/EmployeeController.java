package com.sayu.employee_management_system.controller;

import com.sayu.employee_management_system.dto.EmployeeDto;
import com.sayu.employee_management_system.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;
    @Autowired
    public  EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }
    @PostMapping()
    public @ResponseBody ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto){
        return employeeService.createEmployee(employeeDto);
    }
    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") Long id ,@Valid @RequestBody EmployeeDto employeeDto){
        return employeeService.updateEmployee(id, employeeDto);
    }
    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id){
        return employeeService.deleteEmployee(id);
    }
    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long id){
        return employeeService.getEmployeeById(id);
    }
    @GetMapping()
    public @ResponseBody ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        return employeeService.getAllEmployees();
    }
    @GetMapping("/bloodgroup/{bloodGroup}")
    public @ResponseBody ResponseEntity<Integer> getAllEmployeesCountByBloodGroup(
        @PathVariable("bloodGroup") String bloodGroup){
        return employeeService.getAllEmployeesByBloodGroup(bloodGroup);
    }
    @GetMapping("/by-experiance/{experiance}")
    public @ResponseBody ResponseEntity<List<EmployeeDto>> getAllEmployeesByexperiance(
            @PathVariable("experiance") int experiance){
        return employeeService.getAllEmployeesByexperianceMoreThan(experiance);
    }
    @GetMapping("/married/{yes}")
    public @ResponseBody ResponseEntity<Integer> getAllEmployeesCountByIsMarried(@PathVariable("yes") boolean yes){
        return employeeService.getAllEmployeesByIsMarried(yes);
    }
    @GetMapping("/age-avgsalary/{age}")
    public @ResponseBody ResponseEntity<List<EmployeeDto>> getAllEmployeesCountByAgeAndSalaryAboveAvgSalary(
            @PathVariable("age") int age){
        return employeeService.getAllEmployeesByAgeAndSalaryGreaterThanAverageSalary(age);
    }
    @GetMapping("/employee-average-salary")
    public @ResponseBody ResponseEntity<Double> getAllEmployeesAvgSalary(){
        return employeeService.getAllEmployeesAverageSalary();
    }
}