package com.Scholarship.Tracker.controller;

import com.Scholarship.Tracker.entity.Verification;
import com.Scholarship.Tracker.service.VerificationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verifications")
public class VerificationController {

    private final VerificationService verificationService;

    public VerificationController(
            VerificationService verificationService) {

        this.verificationService = verificationService;
    }

    @GetMapping
    public java.util.List<Verification> getAllVerifications() {
        return verificationService.getAllVerifications();
    }

    @GetMapping("/application/{applicationId}")
    public Verification getVerificationByApplicationId(
            @PathVariable Long applicationId) {

        return verificationService.getVerificationByApplicationId(applicationId);
    }

    @PostMapping
    public Verification createVerification(
            @RequestParam Long applicationId,
            @RequestBody Verification verification) {

        return verificationService.createVerification(
                applicationId,
                verification
        );
    }

    @PutMapping("/{id}/approve")
    public Verification approveVerification(
            @PathVariable Long id) {

        return verificationService.approveVerification(id);
    }

    @PutMapping("/{id}/reject")
    public Verification rejectVerification(
            @PathVariable Long id) {

        return verificationService.rejectVerification(id);
    }
}