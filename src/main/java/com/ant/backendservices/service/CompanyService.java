package com.ant.backendservices.service;

import com.ant.backendservices.model.Company;
import com.ant.backendservices.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Transactional
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }
}
