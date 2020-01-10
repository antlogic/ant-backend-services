package com.ant.backendservices.repository;

import com.ant.backendservices.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
