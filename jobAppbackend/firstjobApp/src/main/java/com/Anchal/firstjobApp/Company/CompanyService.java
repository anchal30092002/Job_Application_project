package com.Anchal.firstjobApp.Company;

import java.util.List;


public interface CompanyService {
    void createCompany(Company company);
    List<Company> findAllCompany();
    Company FindCompanyById(Long id);
    Boolean deleteCompanyById(Long id);
    Company UpdateCompanyById(Company company , Long id);
}
