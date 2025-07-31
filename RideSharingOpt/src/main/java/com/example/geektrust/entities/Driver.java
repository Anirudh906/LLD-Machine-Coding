package com.example.geektrust.entities;

import lombok.Getter;

@Getter
public class Driver {
    private final String driverId;
    private Coordinates driverCoordinates;
    private Boolean isAvailable;

    public Driver(String driverId, Long xCoordinate, Long yCoordinate) {
        this.driverId = driverId;
        this.driverCoordinates = new Coordinates(xCoordinate, yCoordinate);
        this.isAvailable = true;
    }

    public void updateAvailability(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void updateCoordinates(Long xCoordinate, Long yCoordinate) {
        this.driverCoordinates = new Coordinates(xCoordinate, yCoordinate);
    }

    public void assignDriverToRide() {
        this.isAvailable = false;
    }
}