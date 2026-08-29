package com.Anchal.firstjobApp.Job;

import java.util.List;

public interface Jobservice {
    List<Job> findAll();
    boolean createJob(Job job);
    Job getJobByid(Long id);
    boolean deleteJobById(Long id);
    Job updateJob(Job job , Long id);
}
