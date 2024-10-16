package com.gambling.validators;

import com.gambling.configuration.WinCombinationDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import java.util.HashSet;

class SameSymbolValidatorTest {

    @Test
    void testMatchSymbols_SufficientMatches() {

        String[][] matrix = {
            {"X", "O", "X"},
            {"X", "X", "O"},
            {"A", "X", "O"}
        };

        WinCombinationDto winCombinationDto = new WinCombinationDto(1.0, "when", 4, "group", null);
        SameSymbolValidator validator = new SameSymbolValidator(winCombinationDto);

        Set<String> matchingSymbols = validator.matchSymbols(matrix);

        assertEquals(1, matchingSymbols.size());
        assertTrue(matchingSymbols.contains("X"));
    }

    @Test
    void testMatchSymbols_NoSufficientMatches() {
        String[][] matrix = {
            {"X", "O", "X"},
            {"X", "X", "O"},
            {"A", "B", "O"}
        };
        WinCombinationDto winCombinationDto = new WinCombinationDto(1.0, "when", 5, "group", null);
        SameSymbolValidator validator = new SameSymbolValidator(winCombinationDto);

        Set<String> matchingSymbols = validator.matchSymbols(matrix);

        assertEquals(0, matchingSymbols.size());
    }

    @Test
    void testMatchSymbols_MultipleSymbolsMatch() {
        String[][] matrix = {
            {"X", "O", "X"},
            {"O", "O", "X"},
            {"O", "B", "O"}
        };
        WinCombinationDto winCombinationDto = new WinCombinationDto(1.0, "when", 3, "group", null);
        SameSymbolValidator validator = new SameSymbolValidator(winCombinationDto);

        Set<String> matchingSymbols = validator.matchSymbols(matrix);

        assertEquals(2, matchingSymbols.size());
        assertTrue(matchingSymbols.contains("X"));
        assertTrue(matchingSymbols.contains("O"));
    }

    @Test
    void testMatchSymbols_EmptyMatrix() {
        String[][] matrix = {};
        WinCombinationDto winCombinationDto = new WinCombinationDto(1.0, "when", 3, "group", null);
        SameSymbolValidator validator = new SameSymbolValidator(winCombinationDto);

        Set<String> matchingSymbols = validator.matchSymbols(matrix);

        assertEquals(0, matchingSymbols.size());
    }

    @Test
    void testMatchSymbols_MixedSymbols() {
        String[][] matrix = {
            {"X", "Y", "Z"},
            {"A", "B", "C"},
            {"X", "Y", "Z"},
            {"X", "Y", "Z"}
        };
        WinCombinationDto winCombinationDto = new WinCombinationDto(1.0, "when", 3, "group", null);
        SameSymbolValidator validator = new SameSymbolValidator(winCombinationDto);

        Set<String> matchingSymbols = validator.matchSymbols(matrix);

        assertEquals(3, matchingSymbols.size());
        assertTrue(matchingSymbols.contains("X"));
        assertTrue(matchingSymbols.contains("Y"));
        assertTrue(matchingSymbols.contains("Z"));
    }

    @Test
    void testMatchSymbols_NoMatches() {
        String[][] matrix = {
            {"X", "O", "A"},
            {"B", "C", "D"},
            {"E", "F", "G"}
        };
        WinCombinationDto winCombinationDto = new WinCombinationDto(1.0, "when", 2, "group", null);
        SameSymbolValidator validator = new SameSymbolValidator(winCombinationDto);

        Set<String> matchingSymbols = validator.matchSymbols(matrix);

        assertEquals(0, matchingSymbols.size());
    }
}
