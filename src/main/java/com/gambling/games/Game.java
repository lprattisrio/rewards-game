package com.gambling.games;

import com.gambling.configuration.GameConfigurationDto;

public abstract class Game {

    protected GameConfigurationDto configuration;

    protected double bettingAmount;

    protected Game(GameConfigurationDto configuration, double bettingAmount) {
        this.configuration = configuration;
        this.bettingAmount = bettingAmount;
    }

    public final GameResult play() {
        start();
        validateWinningCombinations();
        return calculateReward();
    }

    protected abstract void start();
    protected abstract void validateWinningCombinations();
    protected abstract GameResult calculateReward();
}

