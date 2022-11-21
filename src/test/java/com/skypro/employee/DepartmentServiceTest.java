package com.skypro.employee;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import com.skypro.employee.service.DepartmentService;
import com.skypro.employee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    private final List<Employee> employees = List.of(
            new Employee("Test1", "Test7", 1, 3000),
            new Employee("Test2", "Test8", 2, 3000),
            new Employee("Test3", "Test2", 3, 2000),
            new Employee("Test4", "Test9", 1, 9000),
            new Employee("Test5", "Test10", 2, 6000),
            new Employee("Test6", "Test11", 3, 5000)
    );
    @Mock
    EmployeeService employeeService;
    @InjectMocks
    DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        when(employeeService.getAllEmployees())
                .thenReturn(employees);
    }

    @Test
    void getEmployeesByDepartment() {
        Collection<Employee> employeeList = this.departmentService.getDepartmentEmployees(1);
        org.assertj.core.api.Assertions
                .assertThat(employeeList)
                .hasSize(2)
                .contains(employees.get(0), employees.get(3));

    }

    @Test
    void sumOfSalariesByDepartment() {
        int sum = this.departmentService.getSumOfSalariesByDepartment(1);
        org.assertj.core.api.Assertions
                .assertThat(sum).isEqualTo(12000);
    }

    @Test
    void maxSalaryInDepartment() {
        int max = this.departmentService.getMaxSalaryByDepartment(2);
        org.assertj.core.api.Assertions
                .assertThat(max).isEqualTo(6000);
    }

    @Test
    void minSalaryInDepartment() {
        int min = this.departmentService.getMinSalaryByDepartment(2);
        org.assertj.core.api.Assertions
                .assertThat(min).isEqualTo(3000);
    }

    @Test
    void groupedEmployees() {
        Map<Integer, List<Employee>> groupedEmployees = this.departmentService
                .getEmployeesGroupedByDepartments();

        org.assertj.core.api.Assertions
                .assertThat(groupedEmployees)
                .hasSize(3)
                .containsEntry(1,
                        List.of(employees.get(0), employees.get(3)))
                .containsEntry(2,
                        List.of(employees.get(1), employees.get(4)))
                .containsEntry(3,
                        List.of(employees.get(2), employees.get(5)));
    }

    @Test
    void employeeByDepartment() {
        Stream<Employee> employeeByDepartment = this.departmentService
                .getEmployeeByDepartmentStream(1);

        org.assertj.core.api.Assertions
                .assertThat(employeeByDepartment)
                .hasSize(2)
                .contains(employees.get(0), employees.get(3));
    }

    @Test
    void wnenNoEmployeesThenGroupByReturnEmptyMap() {
        when(employeeService.getAllEmployees()).thenReturn(List.of());
        Map<Integer, List<Employee>> groupedEmployees = this.departmentService
                .getEmployeesGroupedByDepartments();
        org.assertj.core.api.Assertions
                .assertThat(groupedEmployees).isEmpty();
    }

    @Test
    void WhenNoEmployeesThenMaxSalaryInDepartmentThrowsException() {
        when(employeeService.getAllEmployees()).thenReturn(List.of());
        org.assertj.core.api.Assertions
                .assertThatThrownBy(() ->
                        departmentService.getMinSalaryByDepartment(0))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

