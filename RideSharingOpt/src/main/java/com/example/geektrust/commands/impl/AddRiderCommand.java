package com.example.geektrust.commands.impl;

import com.example.geektrust.commands.Command;
import com.example.geektrust.entities.Rider;
import com.example.geektrust.repositories.RiderRepository;

public class AddRiderCommand implements Command {
    private final RiderRepository riderRepository;

    public AddRiderCommand(RiderRepository riderRepository) {
        this.riderRepository = riderRepository;
    }

    @Override
    public void execute(String[] tokens) {
        String riderId = tokens[1];
        long x = Long.parseLong(tokens[2]);
        long y = Long.parseLong(tokens[3]);
        riderRepository.save(new Rider(riderId, x, y));
    }
}