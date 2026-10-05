package com.example.repository;

import com.example.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeesRepository extends JpaRepository<Employees,Long> {

    boolean existsByAdminId(long adminId);
}
