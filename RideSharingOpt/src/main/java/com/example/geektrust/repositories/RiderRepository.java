package com.example.geektrust.repositories;

import com.example.geektrust.entities.Rider;

public interface RiderRepository {
    void save(Rider rider);

    Rider findById(String riderId);

    void update(Rider rider);
}