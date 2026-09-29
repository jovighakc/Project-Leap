package com.Scholarship.Tracker.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    private Double annualIncome;

    private Double marks;

    @Column(name = "marks_percentage", nullable = true)
    private Double marksPercentage;

    @Column(name = "year_of_study", nullable = true)
    private String yearOfStudy;

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(String yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public Double getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(Double annualIncome) {
        this.annualIncome = annualIncome;
    }

    public Double getMarks() {
        return marks != null ? marks : marksPercentage;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
        this.marksPercentage = marks;
    }

    public Double getMarksPercentage() {
        return marksPercentage != null ? marksPercentage : marks;
    }

    public void setMarksPercentage(Double marksPercentage) {
        this.marksPercentage = marksPercentage;
        if (this.marks == null) {
            this.marks = marksPercentage;
        }
    }
}