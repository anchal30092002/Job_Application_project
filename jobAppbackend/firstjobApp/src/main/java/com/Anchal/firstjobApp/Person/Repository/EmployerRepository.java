package com.Anchal.firstjobApp.Person.Repository;

import com.Anchal.firstjobApp.Person.Entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
    Optional findByEmailId(String emailId);
}
