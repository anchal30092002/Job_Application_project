package com.Anchal.firstjobApp.Person.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long educationId;
    private String degree;
    private String university;
    private String college;
    private String stream;
    private Double grade;
    private Date startDate;
    private Date endDate;

    @ManyToOne
    @JoinColumn(name="emailId")
    @JsonBackReference
    private Employee employee;

    public Education() {
    }

    public Education(Long educationId, String degree, String university, String college, String stream, Double grade, Date startDate, Date endDate, Employee employee) {
        this.educationId = educationId;
        this.degree = degree;
        this.university = university;
        this.college = college;
        this.stream = stream;
        this.grade = grade;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employee = employee;
    }

    public Long getEducationId() {
        return educationId;
    }

    public void setEducationId(Long educationId) {
        this.educationId = educationId;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
