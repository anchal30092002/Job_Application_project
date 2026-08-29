package com.Anchal.firstjobApp.Company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController {
    CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/Company")
    public ResponseEntity<String> createCompany(@RequestBody Company company)
    {
        companyService.createCompany(company);
        return new ResponseEntity<>("Company created successfully!!!" , HttpStatus.CREATED);
    }

    @GetMapping("/Company")
    public ResponseEntity<List<Company>> findAllCompany()
    {
        List<Company> companies=companyService.findAllCompany();
        if(companies.size()!=0)
        {
            return new ResponseEntity<>(companies , HttpStatus.FOUND);
        }
        return new ResponseEntity<>(companies , HttpStatus.NOT_FOUND);
    }

    @GetMapping("/Company/{id}")
    public ResponseEntity<Company> FindCompanyById(@PathVariable Long id)
    {
        Company comp=companyService.FindCompanyById(id);
        if(comp!=null)
        {
            return new ResponseEntity<>(comp , HttpStatus.FOUND);
        }
        return new ResponseEntity<>(comp , HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("Company/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable Long id)
    {
        if(companyService.deleteCompanyById(id))
        {
            return new ResponseEntity<>("Company deleted successfully!!!" , HttpStatus.OK);
        }
        return new ResponseEntity<>("Company not found to delete", HttpStatus.NOT_FOUND);
    }

    @PutMapping("Company/{id}")
    public ResponseEntity<Company> UpdateCompanyById(@RequestBody Company company , @PathVariable Long id)
    {
        Company comp=companyService.UpdateCompanyById(company , id);
        if(comp!=null)
        {
            return new ResponseEntity<>(comp , HttpStatus.OK);
        }
        return new ResponseEntity<>(comp , HttpStatus.NOT_FOUND);
    }
}
