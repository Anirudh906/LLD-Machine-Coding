package com.example.geektrust.commands.impl;

import com.example.geektrust.commands.Command;
import com.example.geektrust.services.RideService;

public class MatchCommand implements Command {
    private final RideService rideService;

    public MatchCommand(RideService rideService) {
        this.rideService = rideService;
    }

    @Override
    public void execute(String[] tokens) {
        rideService.match(tokens[1]);
    }
}