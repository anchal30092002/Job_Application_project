package com.Anchal.firstjobApp.Job;

import com.Anchal.firstjobApp.Company.Company;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity   //this is to tell spring boot that this class is a entity for our database. entity represent a table in relational database. and each instance(object) of a entity is a row in a database. entity is also called as pojo(plain old java object). in our case we are using h2 database which is very fast for a small web application project and also light weight.and for using h2 database we are using JPA stands for Java Persistence API.jpa is a specification (a set of rules), not a framework, used in Java to store, retrieve, update, and manage data in a database using objects instead of SQL. jpa generate sql query by own we doesn't need to write query for our crud operation
//@Table(name = "job_table") // to specify table name  . if we doesn't specify table name then it is same as class name
public class Job {
    @Id  //to make below  1 attribute as primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY ) //to manage id attributes . means earlier we are managing it in jobserviceimpl class by increasing it by 1 every time . but using this anotation spring boot will do it by itself.
    private Long id;
    private String title;
    private String description;
    private String minSalary;
    private String maxSalary;
    private String location;

    @ManyToOne
    @JoinColumn(name = "company_id")
    Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Job(Long id, String title, String description, String minSalary, String maxSalary, String location) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.location = location;
    }

    public Job() {  // we are using default constructor beacuse 1. jpa needed it during retrival to data from db. without it jpa won't be able to instantiate any entity in db.
        //2. JPA Uses Reflection to Create Objects means it does not create object using new job()
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(String minSalary) {
        this.minSalary = minSalary;
    }

    public String getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(String maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
