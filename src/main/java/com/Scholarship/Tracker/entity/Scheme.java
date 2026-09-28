package com.Scholarship.Tracker.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "schemes")
public class Scheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String schemeName;

    private String description;

    private Double incomeLimit;

    private Double minimumMarks;

    private Double scholarshipAmount;

    public Scheme() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getIncomeLimit() {
        return incomeLimit;
    }

    public void setIncomeLimit(Double incomeLimit) {
        this.incomeLimit = incomeLimit;
    }

    public Double getMinimumMarks() {
        return minimumMarks;
    }

    public void setMinimumMarks(Double minimumMarks) {
        this.minimumMarks = minimumMarks;
    }

    public Double getScholarshipAmount() {
        return scholarshipAmount;
    }

    public void setScholarshipAmount(Double scholarshipAmount) {
        this.scholarshipAmount = scholarshipAmount;
    }
}