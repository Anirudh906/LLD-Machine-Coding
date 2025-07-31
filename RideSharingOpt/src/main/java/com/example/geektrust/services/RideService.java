package com.example.geektrust.services;

import com.example.geektrust.entities.Coordinates;
import java.util.Map;

public interface RideService {
    void match(String riderId);

    void startRide(String rideId, Integer nthDriver, String riderId);

    void stopRide(String rideId, Coordinates destination, Long timeTaken);

    void generateBill(String rideId);
}