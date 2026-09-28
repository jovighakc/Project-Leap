package com.Scholarship.Tracker.controller;

import com.Scholarship.Tracker.entity.Application;
import com.Scholarship.Tracker.service.ApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(
            ApplicationService applicationService) {

        this.applicationService =
                applicationService;
    }


    // =========================================
    // CREATE APPLICATION
    // =========================================

    @PostMapping
    public Application createApplication(
            @RequestParam Long studentId,
            @RequestParam Long schemeId,
            @RequestParam String document) {

        return applicationService.createApplication(
                studentId,
                schemeId,
                document
        );
    }


    // =========================================
    // GET ALL APPLICATIONS
    // =========================================

    @GetMapping
    public List<Application> getAllApplications() {

        return applicationService
                .getAllApplications();
    }


    // =========================================
    // GET APPLICATION BY ID
    // =========================================

    @GetMapping("/{id}")
    public Application getApplicationById(
            @PathVariable Long id) {

        return applicationService
                .getApplicationById(id);
    }


    // =========================================
    // COMPLETE DISBURSEMENT
    // =========================================

    @PutMapping("/{id}/disburse")
    public Application completeDisbursement(
            @PathVariable Long id) {

        return applicationService
                .completeDisbursement(id);
    }


    // =========================================
    // GET STUDENT APPLICATIONS
    // =========================================

    @GetMapping("/student/{studentId}")
    public List<Application> getApplicationsByStudent(
            @PathVariable Long studentId) {

        return applicationService
                .getApplicationsByStudent(studentId);
    }
}

