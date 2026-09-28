package com.Scholarship.Tracker.service;

import com.Scholarship.Tracker.entity.Scheme;
import com.Scholarship.Tracker.repository.SchemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchemeService {

    private final SchemeRepository schemeRepository;

    public SchemeService(SchemeRepository schemeRepository) {
        this.schemeRepository = schemeRepository;
    }

    public Scheme createScheme(Scheme scheme) {
        return schemeRepository.save(scheme);
    }

    public List<Scheme> getAllSchemes() {
        return schemeRepository.findAll();
    }

    public Scheme getSchemeById(Long id) {
        return schemeRepository.findById(id).orElse(null);
    }

    public Scheme updateScheme(Long id, Scheme scheme) {

        Scheme existing = schemeRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setSchemeName(scheme.getSchemeName());
        existing.setDescription(scheme.getDescription());
        existing.setIncomeLimit(scheme.getIncomeLimit());
        existing.setMinimumMarks(scheme.getMinimumMarks());
        existing.setScholarshipAmount(scheme.getScholarshipAmount());

        return schemeRepository.save(existing);
    }

    public void deleteScheme(Long id) {
        schemeRepository.deleteById(id);
    }
}