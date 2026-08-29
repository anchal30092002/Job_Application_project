package com.Anchal.firstjobApp.Person.impl;

import com.Anchal.firstjobApp.Exception.ResourceNotFoundException;
import com.Anchal.firstjobApp.Person.*;
import com.Anchal.firstjobApp.Person.DTO.UserCredsInput;
import com.Anchal.firstjobApp.Person.DTO.activatePersonOut;
import com.Anchal.firstjobApp.Person.DTO.completeProfileEmployeeOUT;
import com.Anchal.firstjobApp.Person.DTO.completeProfileEmployerOUT;
import com.Anchal.firstjobApp.Person.Entity.*;
import com.Anchal.firstjobApp.Person.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service //using this annotation because this tells spring boot This class contains business logic. Please create and manage an object (bean) of this class.
        //in simple jb hm chahte hai ki ye project start ho to spring khud iska object bna le fir jb hm kisi controller class ya kisi aur class me iska object declare kre tb yahi same object usme inject kre.bina iske hmme object declare krne ke liye new keyword ka use krna padega but iske saath bs class name varible name likh kr kaam chl jata h.
public class personServiceImpl implements personService {

    EmployerRepository employerRepository;
    EmployeeRepository employeeRepository;
    UserCredsRepository userCredsRepository;
    ExperienceRepository experienceRepository;
    EducationRepository educationRepository;
    @Autowired // project start ke time pr jo iska object bna tha wo inject ho jaye isme .
    PasswordEncoder passwordEncoder;

    public personServiceImpl(EmployerRepository employerRepository, EmployeeRepository employeeRepository, UserCredsRepository userCredsRepository, ExperienceRepository experienceRepository, EducationRepository educationRepository) {
        this.employerRepository = employerRepository;
        this.employeeRepository = employeeRepository;
        this.userCredsRepository = userCredsRepository;
        this.experienceRepository = experienceRepository;
        this.educationRepository = educationRepository;
    }

    @Override
    public boolean registerUser(UserCredsInput userCredsInput) {
        Optional<UserCreds> userCreds = userCredsRepository.findByEmailId(userCredsInput.getEmailId());
        if(userCreds.isPresent())
        {
            return false;
        }
        UserCreds newUser = new UserCreds();
        newUser.setEmailId(userCredsInput.getEmailId());
        newUser.setPassword(passwordEncoder.encode(userCredsInput.getPassword()));
        newUser.setActiveSession(true);
        userCredsRepository.save(newUser);
        return  true;
    }

    @Override
    public completeProfileEmployeeOUT completeProfileEmployee(Employee employee) {
        completeProfileEmployeeOUT completeProfileEmployeeOUT = new completeProfileEmployeeOUT();

        //to check if user is registered or not
        Optional<Employee> employeeExist = userCredsRepository.findByEmailId(employee.getEmailId());
        if(employeeExist.isEmpty())
        {
            completeProfileEmployeeOUT.setRegistrationCmpltd(false);
            return completeProfileEmployeeOUT;
        }

        //to check whether user profile is completed or not
        Optional<Employee> emplyExist = employeeRepository.findByEmailId(employee.getEmailId());
        if(emplyExist.isPresent())
        {
            completeProfileEmployeeOUT.setRegistrationCmpltd(true);
            completeProfileEmployeeOUT.setAlreadyCompleted(true);
            return completeProfileEmployeeOUT;
        }
        employee.setActive(true);
        for (Education education : employee.getEducation()) {
            education.setEmployee(employee);
        }

        for (Experience experience : employee.getExperience()) {
            experience.setEmployee(employee);
        }
        employeeRepository.save(employee);
        completeProfileEmployeeOUT.setRegistrationCmpltd(true);
        completeProfileEmployeeOUT.setAlreadyCompleted(false);
        completeProfileEmployeeOUT.setProfileCmpltdNow(true);
        return completeProfileEmployeeOUT;
    }

    @Override
    public completeProfileEmployerOUT completeProfileEmployer(Employer employer) {
        completeProfileEmployerOUT completeProfileEmployerOUT =new completeProfileEmployerOUT();

        //to check if user is present or not
        Optional<Employer> employerExist = userCredsRepository.findByEmailId(employer.getEmailId());
        if(employerExist.isEmpty()) {
            completeProfileEmployerOUT.setRegistrationCmpltd(false);
            return completeProfileEmployerOUT;
        }

        //to check if profile is already completed
        Optional<Employer> emplyrExist = employerRepository.findByEmailId(employer.getEmailId());
        if(emplyrExist.isPresent())
        {
            completeProfileEmployerOUT.setRegistrationCmpltd(true);
            completeProfileEmployerOUT.setAlreadyCompleted(true);
            return completeProfileEmployerOUT;
        }

        employer.setActive(true);
        employerRepository.save(employer);
        completeProfileEmployerOUT.setAlreadyCompleted(false);
        completeProfileEmployerOUT.setProfileCmpltdNow(true);
        completeProfileEmployerOUT.setRegistrationCmpltd(true);
        return completeProfileEmployerOUT;
    }

    @Override
    public boolean deleteEmployee(String emailId , String password) {
        Optional<Employee> employee = employeeRepository.findByEmailId(emailId);
        if(employee.isPresent())
        {
            Employee updatedEmployee = employee.get();
            updatedEmployee.setActive(false);
            employeeRepository.save(updatedEmployee);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteEmployer(String emailId , String password) {
        Optional<Employer> employer = employerRepository.findByEmailId(emailId);
        if(employer.isPresent())
        {
            Employer updatedemployer = employer.get();
            updatedemployer.setActive(false);
            employerRepository.save(updatedemployer);
            return true;
        }
        return false;
    }

    @Override
    public Employee getEmployeeDetails(String emailId, String password) {
        Optional<Employee> employee = employeeRepository.findByEmailId(emailId);
        if(employee.isPresent())
        {
            if(employee.get().isActive())
            return employee.get();
            else
                throw new ResourceNotFoundException("your account is deactivated . Please activate your account before fetching data");

        }
        return null;
    }

    @Override
    public Employer getEmployerDetails(String emailId, String password) {
        Optional<Employer> employer = employerRepository.findByEmailId(emailId);
        if(employer.isPresent())
        {
            if(employer.get().isActive())
            return employer.get();
            else
                throw new ResourceNotFoundException("your account your account is deactivated . Please activate your account before fetching data");
        }
        return null;
    }

    @Override
    public activatePersonOut activateEmployer(String emailId, String password) {
        Optional<Employer> employer = employerRepository.findByEmailId(emailId);
        activatePersonOut activatePersonOut =null;
        if(employer.isEmpty())//employer doesn't exist
        {
            activatePersonOut.setExist(false);
        }
        else{
            Employer updateEmployer = employer.get();
            if(updateEmployer.isActive())
            {
                activatePersonOut.setActivated(true);
            }
            else{
                activatePersonOut.setActivated(false);
                updateEmployer.setActive(true);
                employerRepository.save(updateEmployer);
            }
        }
        return activatePersonOut;
    }

    @Override
    public activatePersonOut activateEmployee(String emailId, String password) {
        Optional<Employee> employee = employeeRepository.findByEmailId(emailId);
        activatePersonOut activatePersonOut=null;
         if(employee.isEmpty())//employee doesnot exist
         {
             activatePersonOut.setExist(false);
         }
         else{
             Employee updatedEmployee =employee.get();
             if(updatedEmployee.isActive())
             {
                 activatePersonOut.setActivated(true);
             }
             else {
                 activatePersonOut.setActivated(false);
                 updatedEmployee.setActive(true);
                 employeeRepository.save(updatedEmployee);
             }
         }
         return activatePersonOut;
    }

    @Override
    public UserCreds loginEmployee(String emailId, String password) {
        Optional<UserCreds> employeeExist = userCredsRepository.findByEmailId(emailId);
        return employeeExist.orElse(null);
    }

    @Override
    public UserCreds loginEmployer(String emailId, String password) {
        Optional<UserCreds> employerExist = userCredsRepository.findByEmailId(emailId);
        return employerExist.orElse(null);
    }
}
