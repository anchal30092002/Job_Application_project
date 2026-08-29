package com.Anchal.firstjobApp.Person.Entity;

import com.Anchal.firstjobApp.Person.Address;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Employee {
   // @EmbeddedId  //here we are using this annotation to tell the spring that "The primary key of this entity is the object of the @Embeddable class."
   // private compositeKey key; ab ye use nhi kr rhe h kyuki key me password bhi hai and password ko hm db me store nhi krta sakte h as a security purpose.

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long employeeId;
    private String emailId;
    private String name;
    private Long phoneNo;
    //resume
    private int noticePeriod;
    @OneToMany(
            mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    //cascade = CascadeType.ALL  means: When I perform an operation on Employee, should Hibernate automatically perform the same operation on its Education objects?  means whenever user add an employee automatically hibernate add its education.  CascadeType.ALL includes PERSIST(Save Employee → save Education.) , MERGE(Update Employee → update associated Education) , REMOVE(Delete Employee → delete associated Education.) , REFRESH , DETACH
    //orphanRemoval = true means:  If an Education is removed from the Employee's education list, Hibernate should delete that Education row from the database.
    //differnce between them ->cascade Controls what happens when you perform an operation on the parent. orphanRemoval Controls what happens when a child is removed from the parent's collection/relationship.
    @JsonManagedReference
    private List<Education> education;
    @OneToMany(
            mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<Experience> experience;
    private String summary;
    @Embedded
    private Address address;
    private String panNo;
    private String citizen;
    private String passport;
    private String gender;
    private Date dob;
    private boolean active;

    public Employee() {
    }

    public Employee(Long employeeId, String emailId, String name, Long phoneNo, int noticePeriod, List<Education> education, List<Experience> experience, String summary, Address address, String panNo, String citizen, String passport, String gender, Date dob, boolean active) {
        this.employeeId = employeeId;
        this.emailId = emailId;
        this.name = name;
        this.phoneNo = phoneNo;
        this.noticePeriod = noticePeriod;
        this.education = education;
        this.experience = experience;
        this.summary = summary;
        this.address = address;
        this.panNo = panNo;
        this.citizen = citizen;
        this.passport = passport;
        this.gender = gender;
        this.dob = dob;
        this.active = active;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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
        this.name = name;
    }

    public Long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public int getNoticePeriod() {
        return noticePeriod;
    }

    public void setNoticePeriod(int noticePeriod) {
        this.noticePeriod = noticePeriod;
    }

    public List<Education> getEducation() {
        return education;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public List<Experience> getExperience() {
        return experience;
    }

    public void setExperience(List<Experience> experience) {
        this.experience = experience;
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
