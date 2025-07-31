package com.example.geektrust.entities;

import lombok.Getter;

@Getter
public class Rider {
    private final String id;
    private Coordinates riderCoordinates;

    public Rider(String id, Long xCoordinate, Long yCoordinate) {
        this.id = id;
        this.riderCoordinates = new Coordinates(xCoordinate, yCoordinate);
    }

    public void updateCoordinates(Coordinates newCoordinates) {
        if (newCoordinates == null) {
            throw new IllegalArgumentException("New coordinates cannot be null");
        }
        this.riderCoordinates =
                new Coordinates(newCoordinates.getXCoordinate(), newCoordinates.getYCoordinate());
    }
}