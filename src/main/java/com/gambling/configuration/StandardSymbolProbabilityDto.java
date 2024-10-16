package com.gambling.configuration;

import java.util.Map;

public record StandardSymbolProbabilityDto(
        int column,
        int row,
        Map<String, Integer> symbols
) {}
