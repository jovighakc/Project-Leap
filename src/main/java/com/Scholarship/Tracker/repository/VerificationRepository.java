package com.Scholarship.Tracker.repository;

import com.Scholarship.Tracker.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
}