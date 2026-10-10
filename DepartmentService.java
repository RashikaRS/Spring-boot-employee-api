package com.example.employeeapi.service;

import com.example.employeeapi.dto.DepartmentRequest;
import com.example.employeeapi.entity.Department;
import com.example.employeeapi.exception.DuplicateResourceException;
import com.example.employeeapi.exception.ResourceNotFoundException;
import com.example.employeeapi.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository repo;

    public DepartmentService(DepartmentRepository repo) {
        this.repo = repo;
    }

    public List<Department> getAll() {
        return repo.findAll();
    }

    public Department getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
    }

    @Transactional
    public Department create(DepartmentRequest req) {
        repo.findByNameIgnoreCase(req.getName()).ifPresent(d -> {
            throw new DuplicateResourceException("Department already exists: " + req.getName());
        });
        return repo.save(new Department(req.getName(), req.getDescription()));
    }

    @Transactional
    public Department update(Long id, DepartmentRequest req) {
        Department d = getById(id);
        repo.findByNameIgnoreCase(req.getName())
            .filter(other -> !other.getId().equals(id))
            .ifPresent(other -> {
                throw new DuplicateResourceException("Department already exists: " + req.getName());
            });
        d.setName(req.getName());
        d.setDescription(req.getDescription());
        return repo.save(d);
    }

    @Transactional
    public void delete(Long id) {
        repo.delete(getById(id));
    }
}
