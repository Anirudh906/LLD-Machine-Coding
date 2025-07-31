package com.example.geektrust.commands.impl;

import com.example.geektrust.commands.Command;
import com.example.geektrust.services.RideService;

public class BillCommand implements Command {
    private final RideService rideService;

    public BillCommand(RideService rideService) {
        this.rideService = rideService;
    }

    @Override
    public void execute(String[] tokens) {
        rideService.generateBill(tokens[1]);
    }
}