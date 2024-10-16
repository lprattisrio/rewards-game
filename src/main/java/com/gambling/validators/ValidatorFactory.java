package com.gambling.validators;

import com.gambling.configuration.WinCombinationDto;

public class ValidatorFactory {

    private ValidatorFactory() {}

    public static Validator getValidator(final WinCombinationDto winCombination) {
        return switch (winCombination.when()) {
            case "same_symbols" -> new SameSymbolValidator(winCombination);
            case "linear_symbols" -> new LinearSymbolValidator(winCombination);
            default -> throw new IllegalArgumentException("Unknown validator type: "
                    + winCombination.group());
        };
    }
}
