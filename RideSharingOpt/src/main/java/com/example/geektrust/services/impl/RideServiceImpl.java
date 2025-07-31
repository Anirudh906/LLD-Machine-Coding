package com.example.geektrust.services.impl;

import static com.example.geektrust.constants.CommonConstants.*;

import com.example.geektrust.entities.Coordinates;
import com.example.geektrust.entities.Driver;
import com.example.geektrust.entities.Ride;
import com.example.geektrust.entities.Rider;
import com.example.geektrust.repositories.DriverRepository;
import com.example.geektrust.repositories.RiderRepository;
import com.example.geektrust.services.RideService;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class RideServiceImpl implements RideService {
    private final Map<String, Ride> rides = new HashMap<>();
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final Map<String, List<String>> riderMatchedDrivers = new HashMap<>();

    public RideServiceImpl(DriverRepository driverRepository, RiderRepository riderRepository) {
        this.driverRepository = driverRepository;
        this.riderRepository = riderRepository;
    }

    @Override
    public void match(String riderId) {
        Rider rider = riderRepository.findById(riderId);
        if (rider == null) {
            System.out.println(INVALID_RIDE);
            return;
        }
        List<Driver> matched = findNearestDrivers(rider);
        if (matched.isEmpty()) {
            System.out.println(NO_DRIVERS_AVAILABLE);
        } else {
            List<String> matchedDriverIds =
                    matched.stream().map(Driver::getDriverId).collect(Collectors.toList());
            riderMatchedDrivers.put(riderId, matchedDriverIds);
            System.out.println(DRIVERS_MATCHED + SPACE + String.join(SPACE, matchedDriverIds));
        }
    }

    @Override
    public void startRide(String rideId, Integer nthDriver, String riderId) {
        List<String> matchedDriverIds = riderMatchedDrivers.get(riderId);
        if (matchedDriverIds == null
                || nthDriver > matchedDriverIds.size()
                || rides.containsKey(rideId)) {
            System.out.println(INVALID_RIDE);
            return;
        }

        String driverId = matchedDriverIds.get(nthDriver - DRIVER_INDEX_OFFSET);
        Driver driver = driverRepository.findById(driverId);
        if (driver == null || !driver.getIsAvailable()) {
            System.out.println(INVALID_RIDE);
            return;
        }

        driver.assignDriverToRide();
        driverRepository.update(driver);
        rides.put(rideId, new Ride(rideId, riderId, driverId));
        System.out.println(RIDE_STARTED + SPACE + rideId);
    }

    @Override
    public void stopRide(String rideId, Coordinates destination, Long timeTaken) {
        Ride ride = rides.get(rideId);
        if (ride == null || ride.getIsRideCompleted()) {
            System.out.println(INVALID_RIDE);
            return;
        }

        Rider rider = riderRepository.findById(ride.getRiderId());
        BigDecimal distance = getDistanceBetweenCoordinates(rider.getRiderCoordinates(), destination);
        ride.completeRide(distance, timeTaken);

        Driver driver = driverRepository.findById(ride.getDriverId());
        driver.updateCoordinates(destination.getXCoordinate(), destination.getYCoordinate());
        driver.updateAvailability(true);
        driverRepository.update(driver);

        rider.updateCoordinates(destination);
        riderRepository.update(rider);

        System.out.println(RIDE_STOPPED + SPACE + rideId);
    }

    @Override
    public void generateBill(String rideId) {
        Ride ride = rides.get(rideId);
        if (ride == null || !ride.getIsRideCompleted()) {
            System.out.println(INVALID_RIDE);
            return;
        }
        BigDecimal billAmount = ride.calculateBill();
        ride.updateRideBill(billAmount);
        System.out.println(
                GET_BILL_COMMAND + SPACE + ride.getRideId() + SPACE + ride.getDriverId() + SPACE + billAmount);
    }

    private List<Driver> findNearestDrivers(Rider rider) {
        Map<String, Driver> allAvailableDrivers = driverRepository.findAllAvailable();
        Coordinates riderCoordinates = rider.getRiderCoordinates();

        return allAvailableDrivers.values().stream()
                .filter(
                        driver ->
                                getDistanceBetweenCoordinates(riderCoordinates, driver.getDriverCoordinates())
                                        .compareTo(BigDecimal.valueOf(MAX_ALLOWED_DISTANCE))
                                        <= COMPARISON_EQUAL_OR_LESS)
                .sorted(
                        Comparator.comparing(
                                        (Driver d) ->
                                                getDistanceBetweenCoordinates(riderCoordinates, d.getDriverCoordinates()))
                                .thenComparing(Driver::getDriverId))
                .collect(Collectors.toList());
    }

    private BigDecimal getDistanceBetweenCoordinates(Coordinates c1, Coordinates c2) {
        double xDiff = c2.getXCoordinate() - c1.getXCoordinate();
        double yDiff = c2.getYCoordinate() - c1.getYCoordinate();
        return BigDecimal.valueOf(Math.sqrt(Math.pow(xDiff, SQUARE_EXPONENT) + Math.pow(yDiff, 2)));
    }
}