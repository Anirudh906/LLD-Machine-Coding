package com.example.geektrust.commands.impl;

import com.example.geektrust.commands.Command;
import com.example.geektrust.services.RideService;

public class StartRideCommand implements Command {
    private final RideService rideService;

    public StartRideCommand(RideService rideService) {
        this.rideService = rideService;
    }

    @Override
    public void execute(String[] tokens) {
        String rideId = tokens[1];
        int nthDriver = Integer.parseInt(tokens[2]);
        String riderId = tokens[3];
        rideService.startRide(rideId, nthDriver, riderId);
    }
}