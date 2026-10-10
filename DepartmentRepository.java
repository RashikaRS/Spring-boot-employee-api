package com.example.employeeapi.repository;

import com.example.employeeapi.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT d FROM Department d WHERE LOWER(d.name) = LOWER(:name)")
    Optional<Department> findByNameIgnoreCase(@Param("name") String name);
}
