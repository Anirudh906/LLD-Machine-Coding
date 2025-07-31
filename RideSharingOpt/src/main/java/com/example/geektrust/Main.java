package com.example.geektrust;

import static com.example.geektrust.constants.CommonConstants.*;

import com.example.geektrust.commands.*;
import com.example.geektrust.commands.impl.*;
import com.example.geektrust.invoker.CommandInvoker;
import com.example.geektrust.repositories.DriverRepository;
import com.example.geektrust.repositories.RiderRepository;
import com.example.geektrust.repositories.impl.DriverRepositoryImpl;
import com.example.geektrust.repositories.impl.RiderRepositoryImpl;
import com.example.geektrust.services.RideService;
import com.example.geektrust.services.impl.RideServiceImpl;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Setup Repositories
        DriverRepository driverRepository = new DriverRepositoryImpl();
        RiderRepository riderRepository = new RiderRepositoryImpl();

        // Setup Services
        RideService rideService = new RideServiceImpl(driverRepository, riderRepository);

        // Setup Commands
        Command addDriverCommand = new AddDriverCommand(driverRepository);
        Command addRiderCommand = new AddRiderCommand(riderRepository);
        Command matchCommand = new MatchCommand(rideService);
        Command startRideCommand = new StartRideCommand(rideService);
        Command stopRideCommand = new StopRideCommand(rideService);
        Command billCommand = new BillCommand(rideService);

        // Setup Invoker
        CommandInvoker invoker = new CommandInvoker();
        invoker.register(ADD_DRIVER_COMMAND, addDriverCommand);
        invoker.register(ADD_RIDER_COMMAND, addRiderCommand);
        invoker.register(MATCH_COMMAND, matchCommand);
        invoker.register(START_RIDE_COMMAND, startRideCommand);
        invoker.register(STOP_RIDE_COMMAND, stopRideCommand);
        invoker.register(GET_BILL_COMMAND, billCommand);

        // Read and process commands from file
        try (FileInputStream fis = new FileInputStream(args[0]);
             Scanner sc = new Scanner(fis)) {
            while (sc.hasNextLine()) {
                invoker.executeCommand(sc.nextLine());
            }
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }
}