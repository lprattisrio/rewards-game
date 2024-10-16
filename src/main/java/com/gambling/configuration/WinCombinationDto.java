package com.gambling.configuration;

public record WinCombinationDto(
        double rewardMultiplier,
        String when,
        int count,
        String group,
        String[][] coveredAreas // nullable, for linear symbols
) {}