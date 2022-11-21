package com.skypro.employee;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import com.skypro.employee.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        this.employeeService = new EmployeeService();
        Stream.of(
                new EmployeeRequest("Test1", "Test7", 1, 3000),
                new EmployeeRequest("Test2", "Test8", 2, 3000),
                new EmployeeRequest("Test3", "Test2", 3, 2000),
                new EmployeeRequest("Test4", "Test9", 1, 9000),
                new EmployeeRequest("Test5", "Test10", 2, 6000),
                new EmployeeRequest("Test6", "Test11", 3, 5000)
        ).forEach(employeeService::addEmployee);
    }

    @Test
    public void addEmployee() {
        EmployeeRequest request = new EmployeeRequest("valid",
                "valid", 3, 5000);
        Employee result = employeeService.addEmployee(request);
        assertEquals(request.getFirstName(), result.getFirstName());
        assertEquals(request.getLastName(), result.getLastName());
        assertEquals(request.getDepartment(), result.getDepartment());
        assertEquals(request.getSalary(), result.getSalary());

        org.assertj.core.api.Assertions
                .assertThat(employeeService.getAllEmployees())
                .contains(result);
    }

    @Test
    public void listEmployees() {
        Collection<Employee> employees = employeeService.getAllEmployees();
        org.assertj.core.api.Assertions
                .assertThat(employees).hasSize(6);

        org.assertj.core.api.Assertions
                .assertThat(employees)
                .first()
                .extracting(Employee::getFirstName)
                .isEqualTo("Test1");
    }

    @Test
    public void sumOfSlaries() {
        int sum = employeeService.getSalarySum();
        org.assertj.core.api.Assertions
                .assertThat(sum).isEqualTo(28000);
    }

    @Test
    public void employeeWithMaxSalary() {
        Employee employee = employeeService.salaryMax();
        org.assertj.core.api.Assertions
                .assertThat(employee)
                .isNotNull()
                .extracting(Employee::getFirstName)
                .isEqualTo("Test4");
    }

    @Test
    public void employeeWithMinSalary() {
        Employee employee = employeeService.salaryMin();
        org.assertj.core.api.Assertions
                .assertThat(employee)
                .isNotNull()
                .extracting(Employee::getFirstName)
                .isEqualTo("Test3");
    }

    @Test
    public void employeesWithSalaryHightAverage() {
        Collection<Employee> employeeList = this.employeeService.averageHighSalary();
        org.assertj.core.api.Assertions
                .assertThat(employeeList)
                .hasSize(3)
                .extracting(Employee::getFirstName)
                .contains("Test4", "Test5", "Test6");

    }

    @Test
    public void removeEmployee() {
        employeeService.removeEmployee(employeeService.getAllEmployees().iterator().next().getId());
        Collection<Employee> employees = employeeService.getAllEmployees();
        org.assertj.core.api.Assertions
                .assertThat(employees)
                .hasSize(5);
    }

}
