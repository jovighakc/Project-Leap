package com.Scholarship.Tracker.service;

import com.Scholarship.Tracker.entity.Application;
import com.Scholarship.Tracker.entity.Scheme;
import com.Scholarship.Tracker.entity.Student;
import com.Scholarship.Tracker.repository.ApplicationRepository;
import com.Scholarship.Tracker.repository.SchemeRepository;
import com.Scholarship.Tracker.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final SchemeRepository schemeRepository;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            StudentRepository studentRepository,
            SchemeRepository schemeRepository) {

        this.applicationRepository = applicationRepository;
        this.studentRepository = studentRepository;
        this.schemeRepository = schemeRepository;
    }

    public Application createApplication(
            Long studentId,
            Long schemeId,
            Application application) {

        Student student =
                studentRepository.findById(studentId).orElse(null);

        Scheme scheme =
                schemeRepository.findById(schemeId).orElse(null);

        if (student == null) {
            throw new RuntimeException(
                    "Student not found with ID: " + studentId
            );
        }

        if (scheme == null) {
            throw new RuntimeException(
                    "Scholarship scheme not found with ID: " + schemeId
            );
        }

        application.setStudent(student);
        application.setScheme(scheme);

        if (student.getAnnualIncome() <= scheme.getIncomeLimit()
                && student.getMarks() >= scheme.getMinimumMarks()) {

            application.setEligibilityStatus("ELIGIBLE");
            application.setApplicationStatus("PENDING");
            application.setDisbursementStatus("NOT_STARTED");

        } else {

            application.setEligibilityStatus("INELIGIBLE");
            application.setApplicationStatus("REJECTED");
            application.setDisbursementStatus("NOT_STARTED");
        }

        return applicationRepository.save(application);
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id).orElse(null);
    }

    public Application updateApplication(
            Long id,
            Application application) {

        Application existing =
                applicationRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setDocument(application.getDocument());
        existing.setApplicationDate(application.getApplicationDate());

        return applicationRepository.save(existing);
    }

    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }

    public Application completeDisbursement(Long id) {

        Application application =
                applicationRepository.findById(id).orElse(null);

        if (application == null) {
            throw new RuntimeException(
                    "Application not found with ID: " + id
            );
        }

        if (!"APPROVED".equals(application.getApplicationStatus())) {
            throw new RuntimeException(
                    "Disbursement cannot be completed. Application must be APPROVED first."
            );
        }

        application.setDisbursementStatus("COMPLETED");

        return applicationRepository.save(application);
    }
}