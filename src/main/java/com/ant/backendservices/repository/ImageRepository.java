package com.ant.backendservices.repository;

import com.ant.backendservices.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<List<Image>> findByCompanyId(Long companyId);
}
