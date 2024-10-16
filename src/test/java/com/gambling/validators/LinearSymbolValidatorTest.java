package com.gambling.validators;

import com.gambling.configuration.WinCombinationDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;

class LinearSymbolValidatorTest {

    @Test
    void testMatchSymbols_AllSymbolsMatch() {
        String[][] coveredAreas = {
                {"0:0", "0:1", "0:2"},
        };

        String[][] matrix = {
                {"X", "X", "X"},
                {"O", "O", "O"},
                {"A", "A", "A"}
        };

        WinCombinationDto winCombinationDto = new WinCombinationDto(1.0, "when", 3, "group", coveredAreas);
        LinearSymbolValidator validator = new LinearSymbolValidator(winCombinationDto);

        Set<String> matchingSymbols = validator.matchSymbols(matrix);

        assertEquals(1, matchingSymbols.size());
        assertTrue(matchingSymbols.contains("X"));
    }

    @Test
    void testMatchSymbols_NoSymbolsMatch() {
        String[][] coveredAreas = {
                {"0:0", "0:1", "0:2"},
        };

        String[][] matrix = {
                {"X", "O", "X"},
                {"O", "O", "O"},
                {"A", "A", "A"}
        };

        WinCombinationDto winCombinationDto = new WinCombinationDto(1.0, "when", 3, "group", coveredAreas);
        LinearSymbolValidator validator = new LinearSymbolValidator(winCombinationDto);

        Set<String> matchingSymbols = validator.matchSymbols(matrix);

        assertEquals(0, matchingSymbols.size());
    }

    @Test
    void testMatchSymbols_MultipleAreasMatching() {
        String[][] coveredAreas = {
                {"0:0", "0:1", "0:2"},
                {"1:0", "1:1", "1:2"},
        };

        String[][] matrix = {
                {"X", "X", "X"},
                {"O", "O", "O"},
                {"A", "B", "C"}
        };

        WinCombinationDto winCombinationDto = new WinCombinationDto(1.0, "when", 3, "group", coveredAreas);
        LinearSymbolValidator validator = new LinearSymbolValidator(winCombinationDto);

        Set<String> matchingSymbols = validator.matchSymbols(matrix);

        assertEquals(2, matchingSymbols.size());
        assertTrue(matchingSymbols.contains("X"));
        assertTrue(matchingSymbols.contains("O"));
    }

    @Test
    void testMatchSymbols_PartialMatch() {
        String[][] coveredAreas = {
                {"0:0", "0:1", "0:2"},
                {"2:0", "2:1", "2:2"},
        };

        String[][] matrix = {
                {"X", "X", "X"},
                {"O", "O", "O"},
                {"A", "B", "C"}
        };

        WinCombinationDto winCombinationDto = new WinCombinationDto(1.0, "when", 3, "group", coveredAreas);
        LinearSymbolValidator validator = new LinearSymbolValidator(winCombinationDto);

        Set<String> matchingSymbols = validator.matchSymbols(matrix);

        assertEquals(1, matchingSymbols.size());
        assertTrue(matchingSymbols.contains("X"));
    }
}
