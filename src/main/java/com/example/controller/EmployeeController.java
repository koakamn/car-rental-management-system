package com.example.controller;

import com.example.entity.Employees;
import com.example.entity.User;
import com.example.service.EmployeesService;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeesService employeeService;

    public EmployeeController(EmployeesService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employees> createEmployee(@RequestParam @Size(max = 40) String email,
                                                    @RequestParam @Size(max = 40) String name,
                                                    @RequestParam @Size(max = 12) String contactNumber) {
        Employees createdEmployee = employeeService.createEmployee(email, name, contactNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdEmployee);
    }

    @GetMapping
    public ResponseEntity<List<Employees>> getAllEmployees() {
        List<Employees> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employees> getEmployee(@PathVariable long id) {
        Employees employee = employeeService.getEmployee(id);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employees> updateEmployee(@PathVariable long id,
                                                    @RequestParam(required = false) @Size(max = 40) String email,
                                                    @RequestParam(required = false) @Size(max = 40) String name,
                                                    @RequestParam(required = false) @Size(max = 12) String contactNumber) {
        Employees updatedEmployee = employeeService.updateEmployee(id, email, name, contactNumber);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/assignDriver")
    public ResponseEntity<Void> assignDriverToBooking(@RequestParam long driverId,
                                                      @RequestParam long bookingId) {
        employeeService.assignDriverToBooking(driverId, bookingId);
        return ResponseEntity
                .noContent()
                .build();
    }


}
