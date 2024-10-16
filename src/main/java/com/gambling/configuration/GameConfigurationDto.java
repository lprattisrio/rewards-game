package com.gambling.configuration;

import java.util.Map;

public record GameConfigurationDto(
        int rows,
        int columns,
        Map<String, SymbolDto> symbols,
        ProbabilitiesDto probabilities,
        Map<String, WinCombinationDto> winCombinations
) {}