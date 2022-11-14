package com.skypro.employee.record;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

public class EmployeeRequest {
    private String firstName;
    private String lastName;
    private int department;
    private int salary;

    public String getFirstName() {
        return StringUtils.capitalize(firstName);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return StringUtils.capitalize(lastName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
