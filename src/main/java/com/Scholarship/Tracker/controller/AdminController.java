package com.Scholarship.Tracker.controller;

import com.Scholarship.Tracker.entity.Admin;
import com.Scholarship.Tracker.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.createAdmin(admin);
    }

    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Admin loginRequest) {
        return adminService.login(loginRequest.getEmail(), loginRequest.getPassword())
                .map(admin -> ResponseEntity.ok((Object) admin))
                .orElseGet(() -> ResponseEntity.status(401).body("Invalid email or password"));
    }
}
