package com.Scholarship.Tracker.repository;

import com.Scholarship.Tracker.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}