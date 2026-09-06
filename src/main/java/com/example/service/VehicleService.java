package com.example.service;

import com.example.entity.User;
import com.example.entity.Vehicle;
import com.example.entity.VehicleAvailability;
import com.example.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle getVehicle(long id){
        return vehicleRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Vehicle with id:" + id + " not found"));
    }

    public List<Vehicle> getAllVehicles(){
        return vehicleRepository.findAll();
    }

    public Vehicle createVehicle(String registrationNumber, String modelNumber){
        if((registrationNumber != null && !registrationNumber.isBlank())
                && (modelNumber != null && !modelNumber.isBlank())){
            Vehicle vehicle = new Vehicle(registrationNumber , modelNumber , VehicleAvailability.AVAILABLE );
            return vehicleRepository.save(vehicle);
        }
        else{
            throw new RuntimeException("Data should not be empty");
        }
    }

    public Vehicle updateVehicle(long id, String registrationNumber, String modelNumber){
        Vehicle vehicle = getVehicle(id);
        if(registrationNumber !=null && !registrationNumber.isBlank()) {
            vehicle.setRegistrationNumber(registrationNumber);
        }
        if(modelNumber !=null && !modelNumber.isBlank()){
            vehicle.setModelNumber(modelNumber);
        }
        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(long id){
        Vehicle vehicle = getVehicle(id);
        if(vehicle.getAvailability() == VehicleAvailability.NON_AVAILABLE){
            throw new RuntimeException("Vehicle is not available for removing");
        }
        vehicleRepository.delete(vehicle);
    }

    public boolean checkAvailability(long id){
        Vehicle vehicle = getVehicle(id);
        return vehicle.getAvailability() == VehicleAvailability.AVAILABLE;
    }

    public void markAsRented(long id){
        Vehicle vehicle = getVehicle(id);
        if(vehicle.getAvailability() == VehicleAvailability.NON_AVAILABLE)throw new RuntimeException("Vehicle is with id " + id +" not available");
        vehicle.setAvailability(VehicleAvailability.NON_AVAILABLE);
        vehicleRepository.save(vehicle);
    }

    public void markAsAvailable(long id){
        Vehicle vehicle = getVehicle(id);
        vehicle.setAvailability(VehicleAvailability.AVAILABLE);
        vehicleRepository.save(vehicle);
    }
}