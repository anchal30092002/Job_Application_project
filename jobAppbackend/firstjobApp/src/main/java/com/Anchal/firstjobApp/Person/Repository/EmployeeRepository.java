package com.Anchal.firstjobApp.Person.Repository;

import com.Anchal.firstjobApp.Person.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional findByEmailId(String emailId);
}
