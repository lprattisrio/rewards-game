package com.gambling.games;

import com.gambling.matrices.MatrixGenerator;
import com.gambling.configuration.GameConfigurationDto;
import com.gambling.configuration.SymbolDto;
import com.gambling.configuration.WinCombinationDto;
import com.gambling.matrices.RandomMatrix;
import com.gambling.validators.ValidatorFactory;

import java.util.*;

public class ScratchRewardsGame extends Game {

    RandomMatrix randomMatrix;

    Map<String, Set<String>> winCombinations;

    public ScratchRewardsGame(GameConfigurationDto configuration, double bettingAmount) {
        super(configuration, bettingAmount);
    }

    @Override
    protected void start() {
        randomMatrix = MatrixGenerator.generateRandomMatrix(configuration);
    }

    @Override
    protected void validateWinningCombinations() {
        final Map<String, Set<String>> winnerCombinations = new HashMap<>();
        final String[][] matrix = randomMatrix.matrix();

       for (Map.Entry<String, WinCombinationDto> combination : configuration.winCombinations().entrySet()) {
           for (String symbol : ValidatorFactory.getValidator(combination.getValue()).matchSymbols(matrix)) {
               winnerCombinations.computeIfAbsent(symbol, v -> new HashSet<>()).add(combination.getKey());
           }
       }
        winnerCombinations.replaceAll(this::reduceGroupCombinations);
        winCombinations = winnerCombinations;
    }

    @Override
    protected GameResult calculateReward() {
        double totalReward = 0;
        final Map<String, SymbolDto> symbols  = configuration.symbols();
        for (Map.Entry<String, Set<String>> entry : winCombinations.entrySet()) {
            double symbolMultiplier = symbols.get(entry.getKey()).rewardMultiplier();
            double combinationMultiplier = entry.getValue().stream()
                    .mapToDouble(s -> configuration.winCombinations().get(s).rewardMultiplier())
                    .reduce(1, (a, b) -> a * b);

            totalReward += bettingAmount * symbolMultiplier * combinationMultiplier;
        }

        totalReward = applyBonus(symbols.get(randomMatrix.bonus()), totalReward);

        return new GameResult(randomMatrix.matrix(), totalReward, winCombinations, randomMatrix.bonus());
    }

    protected double applyBonus(SymbolDto bonus, double reward) {
        return switch (bonus.impact()) {
            case "multiply_reward" -> reward * bonus.rewardMultiplier();
            case "extra_bonus" -> reward + bonus.extra();
            case "miss" -> 0;
            default -> throw new IllegalStateException("Unexpected action for bonus: " + bonus.impact());
        };
    }

    // Assumption: Since the requirements says only one winning combination can be applied
    // for a specific symbol and group, I assume that the highest rewarding combination in the group
    // should be taken into account.
    protected Set<String> reduceGroupCombinations(String key, Set<String> value) {
        Map<String, String> filteredValidations = new HashMap<>();

        for (String combinationName : value) {
            WinCombinationDto combination = configuration.winCombinations().get(combinationName);
            String group = combination.group();
            filteredValidations.merge(group, combinationName, (oldName, newName) ->{
                WinCombinationDto oldCombination = configuration.winCombinations().get(oldName);
                WinCombinationDto newCombination = configuration.winCombinations().get(newName);
                return newCombination.rewardMultiplier() > oldCombination.rewardMultiplier()
                        ? newName : oldName;
            });
        }
        return new HashSet<>(filteredValidations.values());
    }
}

