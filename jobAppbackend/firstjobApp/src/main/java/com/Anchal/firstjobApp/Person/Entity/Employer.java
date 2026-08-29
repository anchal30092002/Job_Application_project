package com.Anchal.firstjobApp.Person.Entity;

import com.Anchal.firstjobApp.Person.Address;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long employerId;
    private String emailId;
    private String name;
    private Long phoneNo;
    private String summary;
    @Embedded
    private Address address;
    private String panNo;
    private String citizen;
    private String passport;
    private String gender;
    private Date dob;
    private boolean active;

    public Employer() {

    }

    public Employer(Long employerId, String emailId, String name, Long phoneNo, String summary, Address address, String panNo, String citizen, String passport, String gender, Date dob, boolean active) {
        this.employerId = employerId;
        this.emailId = emailId;
        this.name = name;
        this.phoneNo = phoneNo;
        this.summary = summary;
        this.address = address;
        this.panNo = panNo;
        this.citizen = citizen;
        this.passport = passport;
        this.gender = gender;
        this.dob = dob;
        this.active = active;
    }

    public Long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Long employerId) {
        this.employerId = employerId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

   public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public Long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getCitizen() {
        return citizen;
    }

    public void setCitizen(String citizen) {
        this.citizen = citizen;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDOB() {
        return dob;
    }

    public void setDOB(Date dob) {
        this.dob = dob;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
