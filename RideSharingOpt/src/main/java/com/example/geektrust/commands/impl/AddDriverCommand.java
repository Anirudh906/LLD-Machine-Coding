package com.example.geektrust.commands.impl;

import com.example.geektrust.commands.Command;
import com.example.geektrust.entities.Driver;
import com.example.geektrust.repositories.DriverRepository;

public class AddDriverCommand implements Command {
    private final DriverRepository driverRepository;

    public AddDriverCommand(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public void execute(String[] tokens) {
        String driverId = tokens[1];
        long x = Long.parseLong(tokens[2]);
        long y = Long.parseLong(tokens[3]);
        driverRepository.save(new Driver(driverId, x, y));
    }
}