package com.gambling.configuration;

public record SymbolDto(
        Double rewardMultiplier,
        String type,
        Integer extra,
        String impact
) {}