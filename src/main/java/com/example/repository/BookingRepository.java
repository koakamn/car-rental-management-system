package com.example.repository;

import com.example.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByVehicleId(long vehicleId);

    boolean existsByDriverId(long driverId);
}

