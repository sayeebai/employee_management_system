package com.sayu.employee_management_system.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    public static final double MINIMUM_SALARY = 10000;
    @NotBlank
    private String name;
    @NonNull
    private int age;
    @NonNull
    @BooleanFlag
    private boolean isMarried;
    @NotBlank
    private String bloodGroup;
    @NonNull
    @PositiveOrZero
    private int experience;
    @NotNull
    @Min(value = (long) MINIMUM_SALARY)
    private double salary;

}
