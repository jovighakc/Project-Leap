package com.Scholarship.Tracker.service;

import com.Scholarship.Tracker.entity.Application;
import com.Scholarship.Tracker.entity.Verification;
import com.Scholarship.Tracker.repository.ApplicationRepository;
import com.Scholarship.Tracker.repository.VerificationRepository;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {

    private final VerificationRepository verificationRepository;
    private final ApplicationRepository applicationRepository;

    public VerificationService(
            VerificationRepository verificationRepository,
            ApplicationRepository applicationRepository) {

        this.verificationRepository = verificationRepository;
        this.applicationRepository = applicationRepository;
    }

    public Verification createVerification(
            Long applicationId,
            Verification verification) {

        Application application =
                applicationRepository.findById(applicationId).orElse(null);

        if (application == null) {
            throw new RuntimeException(
                    "Application not found with ID: " + applicationId
            );
        }

        if (!"ELIGIBLE".equals(application.getEligibilityStatus())) {
            throw new RuntimeException(
                    "Verification cannot be created. Application is INELIGIBLE."
            );
        }

        verification.setApplication(application);

        return verificationRepository.save(verification);
    }
    public Verification approveVerification(Long id) {

        Verification verification =
                verificationRepository.findById(id).orElse(null);

        if (verification == null) {
            return null;
        }

        verification.setStatus("APPROVED");

        Application application =
                verification.getApplication();

        application.setApplicationStatus("APPROVED");

        applicationRepository.save(application);

        return verificationRepository.save(verification);
    }

    public Verification rejectVerification(Long id) {

        Verification verification =
                verificationRepository.findById(id).orElse(null);

        if (verification == null) {
            return null;
        }

        verification.setStatus("REJECTED");

        Application application =
                verification.getApplication();

        application.setApplicationStatus("REJECTED");

        applicationRepository.save(application);

        return verificationRepository.save(verification);
    }
}