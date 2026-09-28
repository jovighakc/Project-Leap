package com.Scholarship.Tracker.repository;

import com.Scholarship.Tracker.entity.Scheme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchemeRepository extends JpaRepository<Scheme, Long> {
}