package com.example.employeeapi.service;

import com.example.employeeapi.dto.EmployeeRequest;
import com.example.employeeapi.dto.EmployeeResponse;
import com.example.employeeapi.entity.Department;
import com.example.employeeapi.entity.Employee;
import com.example.employeeapi.exception.DuplicateResourceException;
import com.example.employeeapi.exception.ResourceNotFoundException;
import com.example.employeeapi.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final DepartmentService departmentService;

    public EmployeeService(EmployeeRepository employeeRepo, DepartmentService departmentService) {
        this.employeeRepo = employeeRepo;
        this.departmentService = departmentService;
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAll() {
        return employeeRepo.findAllWithDepartment().stream().map(EmployeeResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public EmployeeResponse getById(Long id) {
        return EmployeeResponse.from(findEntity(id));
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> getByDepartment(Long deptId) {
        departmentService.getById(deptId); // 404 if department missing
        return employeeRepo.findByDepartmentId(deptId).stream().map(EmployeeResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> search(String keyword) {
        return employeeRepo.searchByName(keyword).stream().map(EmployeeResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> getWithMinSalary(Double min) {
        return employeeRepo.findWithMinSalary(min).stream().map(EmployeeResponse::from).toList();
    }

    @Transactional
    public EmployeeResponse create(EmployeeRequest req) {
        if (employeeRepo.existsByEmail(req.getEmail())) {
            throw new DuplicateResourceException("Email already in use: " + req.getEmail());
        }
        Department dept = departmentService.getById(req.getDepartmentId());
        Employee e = new Employee();
        apply(e, req, dept);
        return EmployeeResponse.from(employeeRepo.save(e));
    }

    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest req) {
        Employee e = findEntity(id);
        if (!e.getEmail().equalsIgnoreCase(req.getEmail()) && employeeRepo.existsByEmail(req.getEmail())) {
            throw new DuplicateResourceException("Email already in use: " + req.getEmail());
        }
        Department dept = departmentService.getById(req.getDepartmentId());
        apply(e, req, dept);
        return EmployeeResponse.from(employeeRepo.save(e));
    }

    @Transactional
    public void delete(Long id) {
        employeeRepo.delete(findEntity(id));
    }

    private Employee findEntity(Long id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
    }

    private void apply(Employee e, EmployeeRequest req, Department dept) {
        e.setName(req.getName());
        e.setEmail(req.getEmail());
        e.setSalary(req.getSalary());
        e.setDepartment(dept);
    }
}
