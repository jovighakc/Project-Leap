package com.Scholarship.Tracker.controller;

import com.Scholarship.Tracker.entity.Application;
import com.Scholarship.Tracker.service.ApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public Application createApplication(
            @RequestParam Long studentId,
            @RequestParam Long schemeId,
            @RequestBody Application application) {

        return applicationService.createApplication(
                studentId,
                schemeId,
                application
        );
    }

    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @GetMapping("/{id}")
    public Application getApplicationById(
            @PathVariable Long id) {

        return applicationService.getApplicationById(id);
    }

    @PutMapping("/{id}")
    public Application updateApplication(
            @PathVariable Long id,
            @RequestBody Application application) {

        return applicationService.updateApplication(
                id,
                application
        );
    }

    @DeleteMapping("/{id}")
    public String deleteApplication(
            @PathVariable Long id) {

        applicationService.deleteApplication(id);

        return "Application deleted successfully";
    }

    @PutMapping("/{id}/disbursement")
    public Application completeDisbursement(
            @PathVariable Long id) {

        return applicationService.completeDisbursement(id);
    }
}