package com.ant.backendservices.repository;

import com.ant.backendservices.model.Display;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisplayRepository extends JpaRepository<Display, Long> {
    Optional<List<Display>> findByCompanyId(Long companyId);
    Optional<List<Display>> findByCompanyIdAndLocationId(Long companyId, Long locationId);
}
