package com.gambling.validators;

import com.gambling.configuration.WinCombinationDto;

import java.util.HashSet;
import java.util.Set;

public class LinearSymbolValidator extends Validator {

    String[][] coveredAreas;

    public LinearSymbolValidator(WinCombinationDto winCombination) {
        coveredAreas = winCombination.coveredAreas();
    }

    @Override
    public Set<String> matchSymbols(String[][] matrix) {

        final Set<String> matchingSymbols = new HashSet<>();

        for (String[] area : coveredAreas) {
            String symbol = null;
            boolean allSymbolsMatch = true;

            for (String position : area) {
                final String[] parts = position.split(":");
                String currentSymbol = matrix[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])];
                if (symbol == null) {
                    symbol = currentSymbol;
                } else if (!symbol.equals(currentSymbol)) {
                    allSymbolsMatch = false;
                    break;
                }
            }

            if (allSymbolsMatch && symbol != null) {
                matchingSymbols.add(symbol);
            }
        }

        return matchingSymbols;
    }
}
