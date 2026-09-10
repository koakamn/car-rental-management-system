package com.example.controller;

import com.example.entity.Employees;
import com.example.entity.User;
import com.example.service.EmployeesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeesService employeeService;

    public EmployeeController(EmployeesService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employees> createEmployee(@RequestParam String email,
                                                    @RequestParam String name,
                                                    @RequestParam String contactNumber) {
        Employees createdEmployee = employeeService.createEmployee(email, name, contactNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employees> getEmployee(@PathVariable long id) {
        Employees employee = employeeService.getEmployee(id);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employees> updateUser(@PathVariable long id,
                                                @RequestParam(required = false) String email,
                                                @RequestParam(required = false) String name,
                                                @RequestParam(required = false) String contactNumber) {
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
