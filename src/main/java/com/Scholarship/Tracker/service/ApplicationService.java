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


    // CREATE APPLICATION
    public Application createApplication(
            Long studentId,
            Long schemeId,
            String document) {

        Student student =
                studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Student not found with ID: "
                                                + studentId
                                )
                        );

        Scheme scheme =
                schemeRepository.findById(schemeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Scholarship scheme not found with ID: "
                                                + schemeId
                                )
                        );


        // AUTOMATIC ELIGIBILITY CHECK

        boolean incomeEligible =
                student.getAnnualIncome()
                        <= scheme.getIncomeLimit();

        boolean marksEligible =
                student.getMarks()
                        >= scheme.getMinimumMarks();

        boolean eligible =
                incomeEligible && marksEligible;


        // CREATE APPLICATION

        Application application =
                new Application();

        application.setStudent(student);

        application.setScheme(scheme);

        application.setDocument(document);


        // IMPORTANT:
        // applicationDate is String in your entity

        application.setApplicationDate(
                LocalDate.now().toString()
        );


        // ELIGIBILITY STATUS

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


        // INITIAL DISBURSEMENT STATUS

        application.setDisbursementStatus(
                "NOT_STARTED"
        );


        return applicationRepository.save(
                application
        );
    }


    // GET ALL APPLICATIONS

    public List<Application> getAllApplications() {

        return applicationRepository.findAll();
    }


    // GET APPLICATION BY ID

    public Application getApplicationById(
            Long id) {

        return applicationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Application not found with ID: "
                                        + id
                        )
                );
    }


    // COMPLETE DISBURSEMENT

    public Application completeDisbursement(
            Long id) {

        Application application =
                applicationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found with ID: "
                                                + id
                                )
                        );


        // DISBURSEMENT ONLY AFTER APPROVAL

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


    // GET APPLICATIONS BY STUDENT

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

