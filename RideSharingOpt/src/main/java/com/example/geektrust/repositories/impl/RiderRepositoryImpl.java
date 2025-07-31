package com.example.geektrust.repositories.impl;

import com.example.geektrust.entities.Rider;
import com.example.geektrust.repositories.RiderRepository;
import java.util.HashMap;
import java.util.Map;

public class RiderRepositoryImpl implements RiderRepository {
    private final Map<String, Rider> riders;

    public RiderRepositoryImpl() {
        this.riders = new HashMap<>();
    }

    @Override
    public void save(Rider rider) {
        riders.put(rider.getId(), rider);
    }

    @Override
    public Rider findById(String riderId) {
        return riders.get(riderId);
    }

    @Override
    public void update(Rider rider) {
        riders.put(rider.getId(), rider);
    }
}