package com.sayu.employee_management_system.repository;

import com.sayu.employee_management_system.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {
    int countAllByBloodGroup(String bloodGroup);

    List<EmployeeEntity> findAllByExperienceGreaterThanEqual(int experience);

    int countAllByIsMarried(boolean yes);

    @Query(value = "SELECT AVG(e.salary) FROM employees e",nativeQuery = true)
    double getAllSalaryAverage();
    @Query(value = "SELECT p FROM employees p WHERE SALARY>=:avgSalary AND AGE<=:age", nativeQuery = true)
    List<EmployeeEntity> findAllBySalaryGreaterThanEqualAndAgeLessThanEqual(Double avgSalary, int age);
}
