package com.example.employeeapi.repository;

import com.example.employeeapi.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmail(String email);

    @Query("SELECT e FROM Employee e JOIN FETCH e.department WHERE e.department.id = :deptId")
    List<Employee> findByDepartmentId(@Param("deptId") Long deptId);

    @Query("SELECT e FROM Employee e JOIN FETCH e.department " +
           "WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Employee> searchByName(@Param("keyword") String keyword);

    @Query("SELECT e FROM Employee e JOIN FETCH e.department WHERE e.salary >= :minSalary ORDER BY e.salary DESC")
    List<Employee> findWithMinSalary(@Param("minSalary") Double minSalary);

    @Query("SELECT e FROM Employee e JOIN FETCH e.department")
    List<Employee> findAllWithDepartment();
}
