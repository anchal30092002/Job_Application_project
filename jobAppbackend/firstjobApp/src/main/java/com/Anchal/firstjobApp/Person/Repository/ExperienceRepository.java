package com.Anchal.firstjobApp.Person.Repository;

import com.Anchal.firstjobApp.Person.Entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
