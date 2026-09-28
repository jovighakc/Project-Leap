package com.Scholarship.Tracker.controller;

import com.Scholarship.Tracker.entity.Scheme;
import com.Scholarship.Tracker.service.SchemeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schemes")
public class SchemeController {

    private final SchemeService schemeService;

    public SchemeController(SchemeService schemeService) {
        this.schemeService = schemeService;
    }

    @PostMapping
    public Scheme createScheme(@RequestBody Scheme scheme) {
        return schemeService.createScheme(scheme);
    }

    @GetMapping
    public List<Scheme> getAllSchemes() {
        return schemeService.getAllSchemes();
    }

    @GetMapping("/{id}")
    public Scheme getSchemeById(@PathVariable Long id) {
        return schemeService.getSchemeById(id);
    }

    @PutMapping("/{id}")
    public Scheme updateScheme(
            @PathVariable Long id,
            @RequestBody Scheme scheme) {

        return schemeService.updateScheme(id, scheme);
    }

    @DeleteMapping("/{id}")
    public String deleteScheme(@PathVariable Long id) {

        schemeService.deleteScheme(id);

        return "Scheme deleted successfully";
    }
}