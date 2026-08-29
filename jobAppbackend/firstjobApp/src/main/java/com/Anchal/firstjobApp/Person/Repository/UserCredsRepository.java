package com.Anchal.firstjobApp.Person.Repository;

import com.Anchal.firstjobApp.Person.Entity.UserCreds;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredsRepository extends JpaRepository<UserCreds, Long> {
    Optional findByEmailId(String emailId);
}
