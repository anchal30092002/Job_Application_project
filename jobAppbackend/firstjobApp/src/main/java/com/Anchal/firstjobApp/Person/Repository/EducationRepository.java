package com.Anchal.firstjobApp.Person.Repository;

import com.Anchal.firstjobApp.Person.Entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
}
