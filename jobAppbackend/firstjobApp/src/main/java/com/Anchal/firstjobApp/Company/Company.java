package com.Anchal.firstjobApp.Company;

import com.Anchal.firstjobApp.Job.Job;
import com.Anchal.firstjobApp.Review.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @JsonIgnore //it is used to ignore recursive call means when we try to get all company or all job our application will throw error because comapny is mapped to job and job is also mapped to company ans it will start a recursive call from comapny to jon or form job to company.
    //@JsonManagedReference
    @OneToMany(mappedBy = "company") //To make below variable one to many relation with our class means one company has many jobs and this mapping is based on company.
    private List<Job> jobs;//here we cannot take jobs as arraylist because Hibernate injects PersistentBag (which implements List), not ArrayList.

    @OneToMany(mappedBy = "company")
    private List<Review> reviews;

    public Company()
    {

    }

    public Company(Long id, String name, String description, List<Job> jobs) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.jobs = jobs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
