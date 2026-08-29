package com.Anchal.firstjobApp.Job.impl;

import com.Anchal.firstjobApp.Company.Company;
import com.Anchal.firstjobApp.Company.CompanyRepository;
import com.Anchal.firstjobApp.Job.Job;
import com.Anchal.firstjobApp.Job.JobRepository;
import com.Anchal.firstjobApp.Job.Jobservice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service   //to tell spring that this is a service class and then spring will provide its object at runtime .
public class JobServiceImpl implements Jobservice {
   // private List<Job> jobs = new ArrayList<>();  we are commenting this because from now we are using jpa repository
    //private long nextId; we are commenting this because now we have defined primary in jpa respository as identity means increases by one every time.

    JobRepository jobRepository;
    CompanyRepository companyRepository;

    public JobServiceImpl(JobRepository jobRepository, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }


    @Override
    public List<Job> findAll() {
        // return jobs;
        return jobRepository.findAll();

    }

    @Override
    public boolean createJob(Job job) {
        //job.setId(nextId++);
        //jobs.add(job);
        Long companyId = job.getCompany().getId();
        Company company = companyRepository.findById(companyId).orElse(null);
        if(company==null)
        {
            return false;
        }
        job.setCompany(company);
        // yaha directly save nhi kr rhe job because directly save krne pr mapping between company and job nhi ho raha tha . isliye phle company with given id dundh ke laaye job me save kiye fir uss job ko save kiye h
        jobRepository.save(job);
        return true;
    }

    @Override
    public Job getJobByid(Long id) {
        /**for(int i=0;i<jobs.size();i++)
        {
            if(jobs.get(i).getId()==id)
            {
                return jobs.get(i);
            }
        }
        return null;**/

        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteJobById(Long id) {
        /**Job job=null;
        for(int i=0;i<jobs.size();i++)
        {
            if(jobs.get(i).getId()==id)
            {
                job= jobs.get(i);
                jobs.remove(i);
            }
        }
        return job;**/
        if(jobRepository.existsById(id))
        {
            jobRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Job updateJob(Job job , Long id) {
        /**for(int i=0;i<jobs.size();i++)
        {
            if(jobs.get(i).getId()==job.getId())
            {
                jobs.get(i).setDescription(job.getDescription());
                jobs.get(i).setTitle(job.getTitle());
                jobs.get(i).setMinSalary(job.getMinSalary());
                jobs.get(i).setMaxSalary(job.getMaxSalary());
                jobs.get(i).setLocation(job.getLocation());
                return jobs.get(i);
            }
        }
        return null;**/

        Optional<Job> jobOptional=jobRepository.findById(id);
        if(jobOptional.isPresent())
        {
            Job updateJob=jobOptional.get();
            updateJob.setDescription(job.getDescription());
            updateJob.setTitle(job.getTitle());
            updateJob.setMinSalary(job.getMinSalary());
            updateJob.setMaxSalary(job.getMaxSalary());
            updateJob.setLocation(job.getLocation());
            jobRepository.save(updateJob);
            return updateJob;
        }
        return null;
    }
}
