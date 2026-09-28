package com.Scholarship.Tracker.service;

import com.Scholarship.Tracker.entity.Application;
import com.Scholarship.Tracker.entity.Verification;
import com.Scholarship.Tracker.repository.ApplicationRepository;
import com.Scholarship.Tracker.repository.VerificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerificationServiceTest {

    @Mock
    private VerificationRepository verificationRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private VerificationService verificationService;

    @Test
    void createVerification_shouldReuseExistingVerificationForSameApplication() {
        Application application = new Application();
        application.setId(1L);
        application.setEligibilityStatus("ELIGIBLE");
        application.setApplicationStatus("PENDING");

        Verification existingVerification = new Verification();
        existingVerification.setId(99L);
        existingVerification.setApplication(application);
        existingVerification.setStatus("PENDING");

        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));
        when(verificationRepository.findByApplicationId(1L)).thenReturn(Optional.of(existingVerification));

        Verification verification = new Verification();

        assertDoesNotThrow(() -> verificationService.createVerification(1L, verification));
    }
}
