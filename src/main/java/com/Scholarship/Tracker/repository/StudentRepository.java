package com.Scholarship.Tracker.repository;

import com.Scholarship.Tracker.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}