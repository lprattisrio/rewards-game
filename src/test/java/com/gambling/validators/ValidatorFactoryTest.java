package com.gambling.validators;

import com.gambling.configuration.WinCombinationDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidatorFactoryTest {

    @Test
    void testGetValidatorSameSymbol() {
        WinCombinationDto winCombination = mock(WinCombinationDto.class);
        when(winCombination.when()).thenReturn("same_symbols");

        Validator validator = ValidatorFactory.getValidator(winCombination);

        assertTrue(validator instanceof SameSymbolValidator);
    }

    @Test
    void testGetValidatorLinearSymbols() {
        WinCombinationDto winCombination = mock(WinCombinationDto.class);
        when(winCombination.when()).thenReturn("linear_symbols");

        Validator validator = ValidatorFactory.getValidator(winCombination);

        assertTrue(validator instanceof LinearSymbolValidator);
    }

    @Test
    void testGetValidatorUnknownType() {
        WinCombinationDto winCombination = mock(WinCombinationDto.class);
        when(winCombination.when()).thenReturn("unknown_type");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ValidatorFactory.getValidator(winCombination);
        });

        assertEquals("Unknown validator type: null", exception.getMessage());
    }
}
