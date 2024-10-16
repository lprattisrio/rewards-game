package com.gambling.configuration;

import com.gambling.exceptions.ScratchGameException;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.*;

class GameConfigurationLoaderTest {

    @Test
    void testGetConfigurationSuccessfully() throws Exception {
        Path tempFile = Files.createTempFile("config", ".json");

        String jsonContent = "{ \"columns\": 4, \"rows\": 4 }";
        Files.write(tempFile, jsonContent.getBytes(), StandardOpenOption.WRITE);

        GameConfigurationDto config = GameConfigurationLoader.getConfiguration(tempFile);

        assertNotNull(config);
        assertEquals(4, config.columns());
        assertEquals(4, config.rows());

        Files.deleteIfExists(tempFile);
    }

    @Test
    void testGetConfigurationThrowsScratchGameException() {
        Path invalidPath = Path.of("invalid_path.json");
        assertThrows(ScratchGameException.class, () -> GameConfigurationLoader.getConfiguration(invalidPath));
    }
}