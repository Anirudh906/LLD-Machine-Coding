package com.example.geektrust.repositories;

import com.example.geektrust.entities.Driver;
import java.util.Map;

public interface DriverRepository {
    void save(Driver driver);

    Map<String, Driver> findAllAvailable();

    Driver findById(String driverId);

    void update(Driver driver);
}