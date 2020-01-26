package com.ant.backendservices.repository;

import com.ant.backendservices.model.PairDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PairDeviceRepository extends JpaRepository<PairDevice, Long> {
    Optional<PairDevice> findByPairCode(String pairCode);
}
