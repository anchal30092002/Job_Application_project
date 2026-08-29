package com.Anchal.firstjobApp.Person;

import com.Anchal.firstjobApp.Person.DTO.UserCredsInput;
import com.Anchal.firstjobApp.Person.DTO.activatePersonOut;
import com.Anchal.firstjobApp.Person.DTO.completeProfileEmployeeOUT;
import com.Anchal.firstjobApp.Person.DTO.completeProfileEmployerOUT;
import com.Anchal.firstjobApp.Person.Entity.Employee;
import com.Anchal.firstjobApp.Person.Entity.Employer;
import com.Anchal.firstjobApp.Person.Entity.UserCreds;

public interface personService {
    boolean registerUser(UserCredsInput userCredsInput);
    completeProfileEmployeeOUT completeProfileEmployee(Employee employee);
    completeProfileEmployerOUT completeProfileEmployer(Employer employer);
    boolean deleteEmployee(String emailId , String password);
    boolean deleteEmployer(String emailId , String password);
    Employee getEmployeeDetails(String emailId , String password);
    Employer getEmployerDetails(String emailId , String password);
    activatePersonOut activateEmployer(String emailId , String password);
    activatePersonOut activateEmployee(String emailId , String password);
    UserCreds loginEmployee(String emailId , String password);
    UserCreds loginEmployer(String emailId , String password);
}
