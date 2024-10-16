package com.gambling;

import com.gambling.Application;
import com.gambling.configuration.GameConfigurationDto;
import com.gambling.configuration.GameConfigurationLoader;
import com.gambling.games.Game;
import com.gambling.games.GameResult;
import com.gambling.games.ScratchRewardsGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationTest {

    @Mock
    private GameConfigurationLoader mockGameConfigurationLoader;

    @Mock
    private ScratchRewardsGame mockGame;

    @Mock
    private GameResult mockGameResult;

    private Path validConfigFilePath;
    private static final double VALID_BETTING_AMOUNT = 50.0;

    @BeforeEach
    void setUp() {
        validConfigFilePath = Paths.get("valid/config/path.json");
    }

    @Test
    void testValidateFilePath_invalidPath() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> Application.validateFilePath("invalid/path/to/file.txt"));
        assertEquals("File does not exist or is not valid: invalid/path/to/file.txt", thrown.getMessage());
    }

    @Test
    void testValidateBettingAmount_validAmount() {
        double result = Application.validateBettingAmount("100");
        assertEquals(100.0, result);
    }

    @Test
    void testValidateBettingAmount_invalidAmount() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> Application.validateBettingAmount("-50"));
        assertEquals("Invalid betting amount '-50'. Must be a positive number", thrown.getMessage());
    }

    @Test
    void testGetArguments_validArguments() throws IOException {
        Path tempFile = Files.createTempFile("config", ".json");

        String[] args = {"--config", tempFile.toString(), "--betting-amount", "100"};

        Application.GameArguments result = Application.getArguments(args);

        assertEquals(tempFile, result.pathFile());
        assertEquals(100.0, result.bettingAmount());

        Files.deleteIfExists(tempFile);
    }

    @Test
    void testGetArguments_missingConfigArgument() {
        String[] args = {"--betting-amount", "100"};
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> Application.getArguments(args));
        assertEquals("Argument '--config' cannot be null", thrown.getMessage());
    }

}
