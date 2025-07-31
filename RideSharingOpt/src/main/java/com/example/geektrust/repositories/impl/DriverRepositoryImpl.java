package com.example.geektrust.repositories.impl;

import com.example.geektrust.entities.Driver;
import com.example.geektrust.repositories.DriverRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DriverRepositoryImpl implements DriverRepository {
    private final Map<String, Driver> drivers;

    public DriverRepositoryImpl() {
        this.drivers = new HashMap<>();
    }

    @Override
    public void save(Driver driver) {
        drivers.put(driver.getDriverId(), driver);
    }

    @Override
    public Map<String, Driver> findAllAvailable() {
        return drivers.entrySet().stream()
                .filter(entry -> entry.getValue().getIsAvailable())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Driver findById(String driverId) {
        return drivers.get(driverId);
    }

    @Override
    public void update(Driver driver) {
        drivers.put(driver.getDriverId(), driver);
    }
}