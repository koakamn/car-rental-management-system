package com.example.controller;

import com.example.entity.Admin;
import com.example.service.AdminService;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
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
    public ResponseEntity<Admin> createAdmin(@RequestParam @Size(max = 20) String name,
                                             @RequestParam @Size(max = 12) String contactNumber,
                                             @RequestParam @Size(max = 50) String email) {
        Admin admin = adminService.createAdmin(name, contactNumber, email);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(admin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable long id,
                                             @RequestParam(required = false) @Size(max = 20) String name,
                                             @RequestParam(required = false) @Size(max = 12) String contactNumber,
                                             @RequestParam(required = false) @Size(max = 50) String email) {
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
