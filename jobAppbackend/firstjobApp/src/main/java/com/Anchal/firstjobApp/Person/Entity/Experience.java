package com.Anchal.firstjobApp.Person.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Experience {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Long experienceID;
        private String companyName;
        private int years;
        private Date startDate;
        private Date endDate;
        private Long salary;
        private Long expectedSalary;
        private String designation;
        private String location;
        private String work;
        private List<String> skills;

        @ManyToOne
        @JoinColumn(name="emailId")
        @JsonBackReference
        private Employee employee;

    public Experience() {
    }

    public Experience(Long experienceID, String companyName, int years, Date startDate, Date endDate, Long salary, Long expectedSalary, String designation, String location, String work, List<String> skills, Employee employee) {
        this.experienceID = experienceID;
        this.companyName = companyName;
        this.years = years;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salary = salary;
        this.expectedSalary = expectedSalary;
        this.designation = designation;
        this.location = location;
        this.work = work;
        this.skills = skills;
        this.employee = employee;
    }

    public Long getExperienceID() {
        return experienceID;
    }

    public void setExperienceID(Long experienceID) {
        this.experienceID = experienceID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Long getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(Long expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
