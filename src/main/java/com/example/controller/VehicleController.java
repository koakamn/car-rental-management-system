package com.example.controller;

import com.example.entity.Employees;
import com.example.entity.Vehicle;
import com.example.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestParam String registrationNumber,
                                                 @RequestParam String modelNumber) {
        Vehicle createdVehicle = vehicleService.createVehicle(registrationNumber, modelNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdVehicle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable long id) {
        Vehicle vehicle = vehicleService.getVehicle(id);
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles(){
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable long id,
                                                 @RequestParam(required = false) String registrationNumber,
                                                 @RequestParam(required = false) String modelNumber) {
        Vehicle updatedVehicle = vehicleService.updateVehicle(id, registrationNumber, modelNumber);
        return ResponseEntity.ok(updatedVehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable long id) {
        boolean isAvailable = vehicleService.checkAvailability(id);
        return ResponseEntity.ok(isAvailable);
    }
}
