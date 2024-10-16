package com.gambling.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.gambling.exceptions.ScratchGameException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class GameConfigurationLoader {

    private GameConfigurationLoader() {
    }

    private static GameConfigurationDto instance;

    private static final ObjectMapper mapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    public static GameConfigurationDto getConfiguration(Path filePath) {
        if (instance == null) {
            instance = readConfiguration(filePath);
        }
        return instance;
    }

    private static GameConfigurationDto readConfiguration(Path filePath) {

        GameConfigurationDto configuration;
        try {
            configuration = mapper.readValue(Files.newInputStream(filePath), GameConfigurationDto.class);
        } catch (IOException e) {
            System.err.println("Error reading the configuration file: " + e.getMessage());
            throw new ScratchGameException(e);
        }
        return configuration;
    }
}
