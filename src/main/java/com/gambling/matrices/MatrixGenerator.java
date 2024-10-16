package com.gambling.matrices;

import com.gambling.configuration.GameConfigurationDto;
import com.gambling.configuration.StandardSymbolProbabilityDto;
import com.gambling.exceptions.ScratchGameException;

import java.util.List;
import java.util.Map;
import java.util.Random;

// TODO: Review possible definition of Matrix<T> class, to deal with Matrix
public class MatrixGenerator {

    private MatrixGenerator() {}

    private static final Random random = new Random();

    public static RandomMatrix generateRandomMatrix(GameConfigurationDto config) {

        final int rows = config.rows();
        final int columns = config.columns();
        final String[][] matrix = new String[rows][columns];

        // Assumption: there is one symbol probability distribution for every cell on the configuration file.
        final var probabilityMatrix = buildProbabilityMatrix(config.probabilities().standardSymbols(), rows, columns);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                final Map<String, Integer> symbolProbability = probabilityMatrix[row][col];
                if (symbolProbability == null)
                    throw new ScratchGameException("Standard symbol probability is not defined" +
                            " for row: " + row + " and column: " + col);

                matrix[row][col] = getRandomSymbolBasedOnProbability(symbolProbability);
            }
        }

        String bonus = null;
        if (config.probabilities().bonusSymbols() != null && config.probabilities().bonusSymbols().symbols() != null) {
            final int row = random.nextInt(matrix.length);
            final int col = random.nextInt(matrix[0].length);
            bonus = getRandomSymbolBasedOnProbability(config.probabilities().bonusSymbols().symbols());
            matrix[row][col] = bonus;
        }
        return new RandomMatrix(matrix, bonus);
    }

    // TODO: Review possible definition of Matrix<T> class, to deal with Matrix
    private static Map<String, Integer>[][] buildProbabilityMatrix(List<StandardSymbolProbabilityDto> symbols, int rows, int columns) {
        final Map<String, Integer>[][] probabilityMatrix = new Map[rows][columns];
        for (StandardSymbolProbabilityDto probability : symbols) {
            final int row = probability.row();
            final int col = probability.column();
            if (row < rows && col < columns) {
                probabilityMatrix[row][col] = probability.symbols();
            }
        }

        return probabilityMatrix;
    }

    private static String getRandomSymbolBasedOnProbability(Map<String, Integer> symbolProbabilities) {
        final int totalProbability = symbolProbabilities.values().stream().mapToInt(Integer::intValue).sum();
        final int randomValue = random.nextInt(totalProbability);

        int cumulativeProbability = 0;
        for (Map.Entry<String, Integer> entry : symbolProbabilities.entrySet()) {
            cumulativeProbability += entry.getValue();
            if (randomValue <= cumulativeProbability) {
                return entry.getKey();
            }
        }
        throw new ScratchGameException("Something went wrong, this is an impossible exception");
    }
}