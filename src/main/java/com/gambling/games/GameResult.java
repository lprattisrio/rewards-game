package com.gambling.games;

import java.util.Map;
import java.util.Set;

public record GameResult(
        String[][] matrix,
        double reward,
        Map<String, Set<String>> appliedWinningCombinations,
        String appliedBonusSymbol) {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\"matrix\":[\n");
        for (String[] row : matrix) {
            sb.append("[");
            for (String element : row) {
                sb.append("\"").append(element).append("\",");
            }
            sb.setLength(sb.length() - 2);
            sb.append("],\n");
        }
        sb.setLength(sb.length() - 2);
        sb.append("\n],\n");

        sb.append("\"reward\": ").append(reward).append(",\n");

        if (appliedWinningCombinations != null && !appliedWinningCombinations.isEmpty()) {
            sb.append("\"applied_winning_combinations\":{\n");
            appliedWinningCombinations.forEach((symbol, combinations) -> {
                sb.append("\"").append(symbol).append("\":").append(combinations).append(",\n");
            });
            sb.setLength(sb.length() - 2);
            sb.append("\n},\n");
        }

        if (appliedBonusSymbol != null && !appliedBonusSymbol.isEmpty()) {
            sb.append("\"applied_bonus_symbol\":\"").append(appliedBonusSymbol).append("\"\n");
        }

        return sb.toString();
    }
}



