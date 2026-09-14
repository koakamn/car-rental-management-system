package com.example.controller;

import com.example.entity.Admin;
import com.example.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdmin(@PathVariable long id) {
        Admin admin = adminService.getAdmin(id);
        return ResponseEntity.ok(admin);
    }

    @GetMapping
    public ResponseEntity<List<Admin>> getAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestParam String name,
                                             @RequestParam String contactNumber,
                                             @RequestParam String email) {
        Admin admin = adminService.createAdmin(name, contactNumber, email);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(admin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable long id,
                                             @RequestParam(required = false) String name,
                                             @RequestParam(required = false) String contactNumber,
                                             @RequestParam(required = false) String email) {
        Admin updatedAdmin = adminService.updateAdmin(id, name, contactNumber, email);
        return ResponseEntity.ok(updatedAdmin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
