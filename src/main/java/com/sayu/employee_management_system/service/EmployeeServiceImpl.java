package com.sayu.employee_management_system.service;

import com.sayu.employee_management_system.dto.EmployeeDto;
import com.sayu.employee_management_system.entity.EmployeeEntity;
import com.sayu.employee_management_system.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private List<EmployeeDto> employeeDtos = new ArrayList<>();

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @PostConstruct
    private void loadDataFromFile() {
        String filePath = "D:/SpringProject/employee_management_system/employeeData.txt";
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            employeeDtos = Collections.unmodifiableList((List<EmployeeDto>) inputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @PreDestroy
    private void saveDataToFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("employeeData.txt"))) {
            outputStream.writeObject(employeeDtos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private EmployeeDto convertEmployeeEntityToDto(EmployeeEntity employeeEntity) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName(employeeEntity.getName());
        employeeDto.setAge(employeeEntity.getAge());
        employeeDto.setSalary(employeeEntity.getSalary());
        employeeDto.setMarried(employeeEntity.isMarried());
        employeeDto.setBloodGroup(employeeEntity.getBloodGroup());
        employeeDto.setExperience(employeeEntity.getExperience());
        return employeeDto;
    }

    private EmployeeEntity convertEmployeeDtoToEntity(EmployeeDto employeeDto) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setName(employeeDto.getName());
        employeeEntity.setAge(employeeDto.getAge());
        employeeEntity.setSalary(employeeDto.getSalary());
        employeeEntity.setMarried(employeeDto.isMarried());
        employeeEntity.setBloodGroup(employeeDto.getBloodGroup());
        employeeEntity.setExperience(employeeDto.getExperience());
        return employeeEntity;
    }

    @Override
    public ResponseEntity<Integer> getAllEmployeesByBloodGroup(String bloodGroup) {
        int count = employeeRepository.countAllByBloodGroup(bloodGroup);
        return ResponseEntity.ok(count);
    }

    @Override
    public ResponseEntity<EmployeeDto> createEmployee(EmployeeDto employeeDto) {
            EmployeeEntity givenEmployeeEntity = convertEmployeeDtoToEntity(employeeDto);
            EmployeeEntity createdEmployeeEntity = employeeRepository.save(givenEmployeeEntity);
            if(createdEmployeeEntity != null) { //always true
                EmployeeDto createdEmployeeDto = convertEmployeeEntityToDto(createdEmployeeEntity);
                return ResponseEntity.ok(createdEmployeeDto);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(employeeDto);
    }

    @Override
    public ResponseEntity<EmployeeDto> updateEmployee(Long employeeId, EmployeeDto employeeDto) {
        if (employeeRepository.existsById(employeeId)) {
            //EmployeeEntity givenEmployeeEntity = convertEmployeeDtoToEntity(employeeDto);
            EmployeeEntity fetchedEmployeeEntity = employeeRepository.findById(employeeId).get();
            //Assigning values
            if (employeeDto.getName() != null && !employeeDto.getName().isEmpty()) {
                fetchedEmployeeEntity.setName(employeeDto.getName());
            }
            if (employeeDto.getAge() > 0) {
                fetchedEmployeeEntity.setAge(employeeDto.getAge());
            }
            if (employeeDto.getBloodGroup() != null && !employeeDto.getBloodGroup().isEmpty()) {
                fetchedEmployeeEntity.setBloodGroup(employeeDto.getBloodGroup());
            }
            if (employeeDto.getExperience() >= 0) {
                fetchedEmployeeEntity.setExperience(employeeDto.getExperience());
            }
            if (employeeDto.getSalary() >= EmployeeDto.MINIMUM_SALARY) {
                fetchedEmployeeEntity.setSalary(employeeDto.getSalary());
            }
            fetchedEmployeeEntity.setMarried(employeeDto.isMarried());
            EmployeeEntity updatedEmployeeEntity = employeeRepository.save(fetchedEmployeeEntity);
            EmployeeDto updatedEmployeeDto = convertEmployeeEntityToDto(updatedEmployeeEntity);
            return ResponseEntity.ok(updatedEmployeeDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(employeeDto);
    }

    @Override
    public ResponseEntity<?> deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(id);
    }
        @Override
        public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
            List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
            List<EmployeeDto> employeeDtos = employeeEntities.stream().map(this::convertEmployeeEntityToDto).
                    collect(Collectors.toList());
            return ResponseEntity.ok(employeeDtos);
        }
        @Override
        public ResponseEntity<EmployeeDto> getEmployeeById(Long employeeId){
            Optional<EmployeeEntity> fetchedEmployeeEntity = employeeRepository.findById(employeeId);
            if(fetchedEmployeeEntity.isPresent()){
                EmployeeDto fetchedEmployeeDto = convertEmployeeEntityToDto(fetchedEmployeeEntity.get());
                return ResponseEntity.ok(fetchedEmployeeDto);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        @Override
        public ResponseEntity<List<EmployeeDto>> getAllEmployeesByexperianceMoreThan(int experiance) {
            List<EmployeeEntity> fetchedEmployeeEntities = employeeRepository.findAllByExperienceGreaterThanEqual(experiance);
            List<EmployeeDto> fetchedEmployeeDtos = fetchedEmployeeEntities.stream()
                    .map(this::convertEmployeeEntityToDto).collect(Collectors.toList());
                    //.map(entity-> {return convertEmployeeEntityToDto(entity);}).collect(Collectors.toList());
            return ResponseEntity.ok().body(fetchedEmployeeDtos);
        }

        @Override
        public ResponseEntity<Integer> getAllEmployeesByIsMarried(boolean yes) {
            int count = employeeRepository.countAllByIsMarried(yes);
            return ResponseEntity.ok().body(count);
        }
        @Override
        public ResponseEntity<List<EmployeeDto>> getAllEmployeesByAgeAndSalaryGreaterThanAverageSalary(int age) {
           Double avgSalary = employeeRepository.getAllSalaryAverage();
           List<EmployeeEntity> fetchedEmplyeesEntity = employeeRepository.findAllBySalaryGreaterThanEqualAndAgeLessThanEqual(avgSalary,age);
           List<EmployeeDto> fetchedEmployeeDtos = fetchedEmplyeesEntity.stream().map(this::convertEmployeeEntityToDto).collect(Collectors.toList());
           return ResponseEntity.ok().body(fetchedEmployeeDtos);
        }
        @Override
        public ResponseEntity<Double> getAllEmployeesAverageSalary() {
            Double avgSalary = employeeRepository.getAllSalaryAverage();
            return ResponseEntity.ok().body(avgSalary);
        }
}