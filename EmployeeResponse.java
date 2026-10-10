package com.example.employeeapi.dto;

import com.example.employeeapi.entity.Employee;

public class EmployeeResponse {
    private Long id;
    private String name;
    private String email;
    private Double salary;
    private Long departmentId;
    private String departmentName;

    public static EmployeeResponse from(Employee e) {
        EmployeeResponse r = new EmployeeResponse();
        r.id = e.getId();
        r.name = e.getName();
        r.email = e.getEmail();
        r.salary = e.getSalary();
        r.departmentId = e.getDepartment().getId();
        r.departmentName = e.getDepartment().getName();
        return r;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Double getSalary() { return salary; }
    public Long getDepartmentId() { return departmentId; }
    public String getDepartmentName() { return departmentName; }
}
