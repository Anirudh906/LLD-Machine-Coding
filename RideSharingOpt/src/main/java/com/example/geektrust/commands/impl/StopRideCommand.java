package com.example.geektrust.commands.impl;

import com.example.geektrust.commands.Command;
import com.example.geektrust.entities.Coordinates;
import com.example.geektrust.services.RideService;

public class StopRideCommand implements Command {
    private final RideService rideService;

    public StopRideCommand(RideService rideService) {
        this.rideService = rideService;
    }

    @Override
    public void execute(String[] tokens) {
        String rideId = tokens[1];
        long x = Long.parseLong(tokens[2]);
        long y = Long.parseLong(tokens[3]);
        long time = Long.parseLong(tokens[4]);
        rideService.stopRide(rideId, new Coordinates(x, y), time);
    }
}