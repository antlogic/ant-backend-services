package com.ant.backendservices.repository;

import com.ant.backendservices.model.Slide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SlideRepository extends JpaRepository<Slide, Long> {
    Optional<List<Slide>> findByCompanyIdAndLocations_IdAndDisplays_Id(Long companyId, Long locationId, Long displayId);

    Optional<List<Slide>> findByCompanyId(Long companyId);
}
