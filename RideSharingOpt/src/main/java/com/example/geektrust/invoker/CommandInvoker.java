package com.example.geektrust.invoker;

import com.example.geektrust.commands.Command;
import java.util.HashMap;
import java.util.Map;

public class CommandInvoker {
    private final Map<String, Command> commandMap = new HashMap<>();

    public void register(String commandName, Command command) {
        commandMap.put(commandName, command);
    }

    public void executeCommand(String commandLine) {
        String[] tokens = commandLine.split(" ");
        String commandName = tokens[0];
        Command command = commandMap.get(commandName);
        if (command != null) {
            command.execute(tokens);
        } else {
            System.out.println("Invalid command: " + commandName);
        }
    }
}