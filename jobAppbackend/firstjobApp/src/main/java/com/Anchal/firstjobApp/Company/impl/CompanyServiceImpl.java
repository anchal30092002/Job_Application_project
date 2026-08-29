package com.Anchal.firstjobApp.Company.impl;

import com.Anchal.firstjobApp.Company.Company;
import com.Anchal.firstjobApp.Company.CompanyRepository;
import com.Anchal.firstjobApp.Company.CompanyService;
import com.Anchal.firstjobApp.Exception.ResourceConflictException;
import com.Anchal.firstjobApp.Exception.ResourceNotFoundException;
import com.Anchal.firstjobApp.Job.JobRepository;
import com.Anchal.firstjobApp.Review.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService{
    CompanyRepository companyRepository;
    ReviewRepository reviewRepository;
    JobRepository jobRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository , ReviewRepository reviewRepository , JobRepository jobRepository) {
        this.companyRepository = companyRepository;
        this.reviewRepository = reviewRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    public void createCompany(Company company) {
         companyRepository.save(company);
    }

    @Override
    public List<Company> findAllCompany() {
        return companyRepository.findAll();
    }

    @Override
    public Company FindCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean deleteCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Company not found")); //here we are using predefined exception
        boolean job = jobRepository.existsByCompanyId(id);
        boolean review = reviewRepository.existsByCompanyId(id);
        if(job || review)
        {
            throw new ResourceConflictException("Cannot delete company as jobs or reviews are associated with it");//here also we are using pre defined exception.
        }
        companyRepository.deleteById(id);
        return true;
    }

    @Override
    public Company UpdateCompanyById(Company company, Long id) {
        if(companyRepository.existsById(id))
        {
            Optional<Company> optional=companyRepository.findById(id);
            if(optional.isPresent())
            {
                Company updatedCompany=optional.get();
                updatedCompany.setDescription(company.getDescription());
                updatedCompany.setName(company.getName());
                updatedCompany.setJobs(company.getJobs());
                companyRepository.save(updatedCompany);
                return updatedCompany;
            }
        }
        return null;
    }
}
