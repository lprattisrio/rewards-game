package com.gambling.validators;

import com.gambling.configuration.WinCombinationDto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SameSymbolValidator extends Validator {

    private final int numberOfMatches;

    public SameSymbolValidator(WinCombinationDto winCombinationDto) {
        this.numberOfMatches = winCombinationDto.count();
    }

    @Override
    public Set<String> matchSymbols(String[][] matrix) {
        final Map<String, Integer> symbolCount = new HashMap<>();
        final Set<String> matchingSymbols = new HashSet<>();

        for (String[] strings : matrix) {
            for (String symbol : strings) {
                if (!matchingSymbols.contains(symbol)) {
                    symbolCount.put(symbol, symbolCount.getOrDefault(symbol, 0) + 1);
                    if (symbolCount.get(symbol) >= numberOfMatches) {
                        matchingSymbols.add(symbol);
                    }
                }
            }
        }

        return matchingSymbols;
    }
}
