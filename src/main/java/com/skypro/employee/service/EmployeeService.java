package com.skypro.employee.service;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final Map<Integer, Employee> employees = new HashMap<>();


    public Collection<Employee> getAllEmployees() {
        return this.employees.values();
    }

    public Employee addEmployee(EmployeeRequest employeeRequest) {
        if (employeeRequest.getFirstName() == null
                || StringUtils.isBlank(employeeRequest.getFirstName())
                || StringUtils.isEmpty(employeeRequest.getFirstName())
        ) {
            throw new IllegalArgumentException("Надо добавить имя");
        }
        if (employeeRequest.getLastName() == null
                || StringUtils.isBlank(employeeRequest.getLastName())
                || StringUtils.isEmpty(employeeRequest.getLastName())
        ) {
            throw new IllegalArgumentException("Надо добавить фамилию");
        }
        Employee employee = new Employee(employeeRequest.getFirstName(),
                employeeRequest.getLastName(),
                employeeRequest.getDepartment(),
                employeeRequest.getSalary());
        this.employees.put(employee.getId(), employee);
        return employee;
    }

    public int getSalarySum() {
        return employees.values().stream()
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public Employee salaryMin() {
        return employees.values().stream()
                .min(Comparator.comparingInt(Employee::getSalary)).get();
    }

    public Employee salaryMax() {
        return employees.values().stream()
                .max(Comparator.comparingInt(Employee::getSalary)).get();
    }

    public Collection<Employee> averageHighSalary() {
        double d = employees.values().stream()
                .mapToInt(Employee::getSalary)
                .average().getAsDouble();
        return employees.values().stream()
                .filter(employee -> employee.getSalary() > d).collect(Collectors.toList());
    }

    public Employee removeEmployee(int id) {
        return employees.remove(id);
    }

}
