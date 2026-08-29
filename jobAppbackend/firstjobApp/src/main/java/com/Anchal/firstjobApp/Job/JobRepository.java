package com.Anchal.firstjobApp.Job;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job , Long> {  //here job means we are informing  spring boot that job is entity and long informs that it is datatype of primary key
    boolean existsByCompanyId(Long id);
}
//instead if JpaRepository we can also use CRUDRepository . it provides all crud query. basically JpaRepository is an extended versiom of CrudRepository.
//benefits of using jpaRepository is that we doesnot need to write implentation or any code it automatically provides all implementation.