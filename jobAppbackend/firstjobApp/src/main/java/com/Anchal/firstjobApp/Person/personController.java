package com.Anchal.firstjobApp.Person;

import com.Anchal.firstjobApp.Person.DTO.UserCredsInput;
import com.Anchal.firstjobApp.Person.DTO.activatePersonOut;
import com.Anchal.firstjobApp.Person.DTO.completeProfileEmployeeOUT;
import com.Anchal.firstjobApp.Person.DTO.completeProfileEmployerOUT;
import com.Anchal.firstjobApp.Person.Entity.Employee;
import com.Anchal.firstjobApp.Person.Entity.Employer;
import com.Anchal.firstjobApp.Person.Entity.UserCreds;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class personController {

    personService personService;

    public personController(com.Anchal.firstjobApp.Person.personService personService) {
        this.personService = personService;
    }

    @PostMapping("/RegisterUser")
    public ResponseEntity<String> RegisterUser(@RequestBody UserCredsInput userCredsInput)
    {
        if(personService.registerUser(userCredsInput))
        {
            return new ResponseEntity<>("Profile created successfully!!!!" , HttpStatus.OK);
        }
        return new ResponseEntity<>("Profile already exist" , HttpStatus.CONFLICT);
    }

    @PostMapping("/completeProfileEmployee")
    public ResponseEntity<String> completeProfileEmployee(@RequestBody Employee employee)
    {
        completeProfileEmployeeOUT completeProfileEmployeeOUT = personService.completeProfileEmployee(employee);
        if(completeProfileEmployeeOUT.isRegistrationCmpltd()==false)
        {
            return new ResponseEntity<>("Profile does not exist!!!" , HttpStatus.NOT_FOUND);
        }
        if(completeProfileEmployeeOUT.isAlreadyCompleted())
        {
            return new ResponseEntity<>("profile already completed!!! you can now only update your profile", HttpStatus.BAD_REQUEST);
        }
        if(completeProfileEmployeeOUT.isProfileCmpltdNow())
        {
            return new ResponseEntity<>("profile completed!!!" , HttpStatus.OK);
        }
        return new ResponseEntity<>("Error in completing profile" , HttpStatus.CONFLICT);
    }

    @PostMapping("/completeProfileEmployer")
    public ResponseEntity<String> completeProfileEmployer(@RequestBody Employer employer)
    {
        completeProfileEmployerOUT completeProfileEmployerOUT =personService.completeProfileEmployer(employer);
        if(completeProfileEmployerOUT.isRegistrationCmpltd()==false)//means user doesnot exist
        {
            return new ResponseEntity<>("Profile does not exist!!!" , HttpStatus.NOT_FOUND);
        }
        if(completeProfileEmployerOUT.isAlreadyCompleted())
        {
            return new ResponseEntity<>("Profile is already completed . Now you can only update your profile" , HttpStatus.OK);
        }
        if(completeProfileEmployerOUT.isProfileCmpltdNow())
        {
            return new ResponseEntity<>("profile completed!!!" , HttpStatus.OK);
        }
        return new ResponseEntity<>("Error in completing profile" , HttpStatus.CONFLICT);

    }

    @DeleteMapping("/deleteEmployee/{emailId}/{password}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String emailId , @PathVariable String password)
    {
        if(personService.deleteEmployee(emailId, password))
        {
            return new ResponseEntity<>("your account is deleted successfully!!!!" , HttpStatus.OK);
        }
        return new ResponseEntity<>("Account not found" , HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteEmployer/{emailId}/{password}")
    public ResponseEntity<String> deleteEmployer(@PathVariable String emailId , @PathVariable String password)
    {
        if(personService.deleteEmployer(emailId, password))
        {
            return new ResponseEntity<>("your account is deleted successfully!!!!" , HttpStatus.OK);
        }
        return new ResponseEntity<>("Account not found" , HttpStatus.NOT_FOUND);
    }

    @GetMapping("/myProfileEmployee/{emailId}/{password}")
    public ResponseEntity<Employee> getEmployeeDetails(@PathVariable String emailId ,@PathVariable String password)
    {
        Employee employee = personService.getEmployeeDetails(emailId, password)  ;
        if(employee!=null)
        {
            return new ResponseEntity<>(employee , HttpStatus.OK);
        }
        return new ResponseEntity<>(employee , HttpStatus.NOT_FOUND);
    }

    @GetMapping("/myProfileEmployer/{emailId}/{password}")
    public ResponseEntity<Employer> getEmployerDetails(@PathVariable String emailId ,@PathVariable String password)
    {
        Employer employer = personService.getEmployerDetails(emailId, password);
        if(employer!=null)
        {
            return new ResponseEntity<>(employer , HttpStatus.OK);
        }
        return new ResponseEntity<>(employer , HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/activateEmployee/{emailId}/{password}")
    public ResponseEntity<String> activateEmployee(@PathVariable String emailId ,@PathVariable String password)
    {
        activatePersonOut activatePersonOut = personService.activateEmployee(emailId, password);
        if(!activatePersonOut.isExist())
        {
            return new ResponseEntity<>("Employee data doesn't exist .Please register yourself first..." , HttpStatus.NOT_FOUND);
        }
        else {
            if(activatePersonOut.isActivated())
            {
                return new ResponseEntity<>("you are already activated" , HttpStatus.BAD_REQUEST);
            }
            else{
                return new ResponseEntity<>("your data is activated . Now you can proceed further and apply for job accordingly" , HttpStatus.OK);
            }
        }
    }

    @PatchMapping("/activateEmployer/{emailId}/{password}")
    public ResponseEntity<String> activateEmployer(@PathVariable String emailId,  @PathVariable String password)
    {
        activatePersonOut activatePersonOut = personService.activateEmployer(emailId, password);
        if(!activatePersonOut.isExist())
        {
            return new ResponseEntity<>("Employee data doesn't exist .Please register yourself first..." , HttpStatus.NOT_FOUND);
        }
        else {
            if(activatePersonOut.isActivated())
            {
                return new ResponseEntity<>("you are already activated" , HttpStatus.BAD_REQUEST);
            }
            else{
                return new ResponseEntity<>("your data is activated . Now you can procedd further accordingly" , HttpStatus.OK);
            }
        }
    }

    @GetMapping("/loginEmployee/{emailId}/{password}")
    public ResponseEntity<?> loginEmployee(@PathVariable String emailId , @PathVariable String password)
    {
        UserCreds userCreds = personService.loginEmployee(emailId, password);
        if(userCreds!=null)
        {
            return new ResponseEntity<>(userCreds , HttpStatus.OK);
        }
        return new ResponseEntity<>(false , HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/loginEmployer/{emailId}/{password}")
    public ResponseEntity<?> loginEmployer(@PathVariable String emailId , @PathVariable String password)
    {
        UserCreds userCreds = personService.loginEmployer(emailId, password);
        if(userCreds!=null)
        {
            return new ResponseEntity<>(userCreds , HttpStatus.OK);
        }
        return new ResponseEntity<>(false , HttpStatus.BAD_REQUEST);
    }
}
