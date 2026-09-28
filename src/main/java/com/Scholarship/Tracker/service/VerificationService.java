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


    // =========================================
    // CREATE VERIFICATION
    // =========================================

    public Verification createVerification(
            Long applicationId,
            Verification verification) {


        Application application =
                applicationRepository
                        .findById(applicationId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Application not found with ID: "
                                                + applicationId
                                )
                        );


        // =========================================
        // ELIGIBILITY CHECK
        // =========================================

        if (!"ELIGIBLE".equals(
                application.getEligibilityStatus())) {

            throw new RuntimeException(
                    "Verification cannot be created. " +
                            "Application is not eligible."
            );
        }


        // =========================================
        // APPLICATION STATUS CHECK
        // =========================================

        if ("REJECTED".equals(
                application.getApplicationStatus())) {

            throw new RuntimeException(
                    "Verification cannot be created. " +
                            "Application is already rejected."
            );
        }


        // =========================================
        // CONNECT APPLICATION
        // =========================================

        verification.setApplication(
                application
        );


        // =========================================
        // INITIAL STATUS
        // =========================================

        verification.setStatus(
                "PENDING"
        );


        // =========================================
        // SAVE
        // =========================================

        return verificationRepository.save(
                verification
        );
    }


    // =========================================
    // APPROVE VERIFICATION
    // =========================================

    public Verification approveVerification(
            Long id) {


        Verification verification =
                verificationRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Verification not found with ID: "
                                                + id
                                )
                        );


        // =========================================
        // APPROVE
        // =========================================

        verification.setStatus(
                "APPROVED"
        );


        Application application =
                verification.getApplication();


        application.setApplicationStatus(
                "APPROVED"
        );


        // =========================================
        // SAVE APPLICATION
        // =========================================

        applicationRepository.save(
                application
        );


        // =========================================
        // SAVE VERIFICATION
        // =========================================

        return verificationRepository.save(
                verification
        );
    }


    // =========================================
    // REJECT VERIFICATION
    // =========================================

    public Verification rejectVerification(
            Long id) {


        Verification verification =
                verificationRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Verification not found with ID: "
                                                + id
                                )
                        );


        // =========================================
        // REJECT
        // =========================================

        verification.setStatus(
                "REJECTED"
        );


        Application application =
                verification.getApplication();


        application.setApplicationStatus(
                "REJECTED"
        );


        // =========================================
        // SAVE APPLICATION
        // =========================================

        applicationRepository.save(
                application
        );


        // =========================================
        // SAVE VERIFICATION
        // =========================================

        return verificationRepository.save(
                verification
        );
    }
}
