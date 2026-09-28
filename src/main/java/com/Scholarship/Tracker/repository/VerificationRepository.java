package com.Scholarship.Tracker.repository;

import com.Scholarship.Tracker.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
    boolean existsByApplicationId(Long applicationId);
    Optional<Verification> findByApplicationId(Long applicationId);
}