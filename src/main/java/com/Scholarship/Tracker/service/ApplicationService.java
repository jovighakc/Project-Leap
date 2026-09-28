package com.Scholarship.Tracker.service;

import com.Scholarship.Tracker.entity.Application;
import com.Scholarship.Tracker.entity.Scheme;
import com.Scholarship.Tracker.entity.Student;
import com.Scholarship.Tracker.repository.ApplicationRepository;
import com.Scholarship.Tracker.repository.SchemeRepository;
import com.Scholarship.Tracker.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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


    // =========================================
    // CREATE APPLICATION
    // =========================================

    public Application createApplication(

            Long studentId,

            Long schemeId,

            Double marks,

            Double annualIncome,

            String document) {


        // FIND STUDENT

        Student student =
                studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Student not found with ID: "
                                                + studentId
                                )
                        );


        // FIND SCHOLARSHIP SCHEME

        Scheme scheme =
                schemeRepository.findById(schemeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Scholarship scheme not found with ID: "
                                                + schemeId
                                )
                        );


        // VALIDATE MARKS

        if (marks == null ||
                marks < 0 ||
                marks > 100) {

            throw new RuntimeException(
                    "Marks must be between 0 and 100."
            );
        }


        // VALIDATE ANNUAL INCOME

        if (annualIncome == null ||
                annualIncome < 0) {

            throw new RuntimeException(
                    "Annual income cannot be negative."
            );
        }


        // =========================================
        // ELIGIBILITY CHECK
        // =========================================

        boolean incomeEligible =
                annualIncome <=
                        scheme.getIncomeLimit();


        boolean marksEligible =
                marks >=
                        scheme.getMinimumMarks();


        boolean eligible =
                incomeEligible &&
                        marksEligible;


        // =========================================
        // CREATE APPLICATION
        // =========================================

        Application application =
                new Application();


        application.setStudent(student);

        application.setScheme(scheme);


        // SAVE APPLY-TIME DETAILS

        application.setMarks(marks);

        application.setAnnualIncome(annualIncome);


        // SAVE DOCUMENT INFORMATION

        application.setDocument(document);


        // APPLICATION DATE

        application.setApplicationDate(
                LocalDate.now().toString()
        );


        // =========================================
        // ELIGIBILITY STATUS
        // =========================================

        if (eligible) {

            application.setEligibilityStatus(
                    "ELIGIBLE"
            );

            application.setApplicationStatus(
                    "PENDING"
            );

        } else {

            application.setEligibilityStatus(
                    "NOT_ELIGIBLE"
            );

            application.setApplicationStatus(
                    "REJECTED"
            );
        }


        // =========================================
        // DISBURSEMENT STATUS
        // =========================================

        application.setDisbursementStatus(
                "NOT_STARTED"
        );


        // SAVE

        return applicationRepository.save(
                application
        );
    }


    // =========================================
    // GET ALL APPLICATIONS
    // =========================================

    public List<Application> getAllApplications() {

        return applicationRepository.findAll();
    }


    // =========================================
    // GET APPLICATION BY ID
    // =========================================

    public Application getApplicationById(
            Long id) {

        return applicationRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Application not found with ID: "
                                        + id
                        )
                );
    }


    // =========================================
    // COMPLETE DISBURSEMENT
    // =========================================

    public Application completeDisbursement(
            Long id) {

        Application application =
                applicationRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found with ID: "
                                                + id
                                )
                        );


        if (!"APPROVED".equals(
                application.getApplicationStatus())) {

            throw new RuntimeException(
                    "Disbursement cannot be completed. "
                            + "Application verification is not approved."
            );
        }


        application.setDisbursementStatus(
                "COMPLETED"
        );


        return applicationRepository.save(
                application
        );
    }


    // =========================================
    // GET STUDENT APPLICATIONS
    // =========================================

    public List<Application> getApplicationsByStudent(
            Long studentId) {


        studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found with ID: "
                                        + studentId
                        )
                );


        return applicationRepository
                .findByStudentId(studentId);
    }
}
