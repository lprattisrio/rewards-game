package com.gambling;

import com.gambling.configuration.GameConfigurationDto;
import com.gambling.configuration.GameConfigurationLoader;
import com.gambling.games.Game;
import com.gambling.games.GameResult;
import com.gambling.games.ScratchRewardsGame;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Application {

    public static final String FILEPATH_CONFIG_ARGUMENT = "--config";
    public static final String BETTING_AMOUNT_ARGUMENT = "--betting-amount";

    public static void main(String[] args ) {
        // 1 - Get arguments to get configuration and capture betting amount.
        GameArguments arguments = getArguments(args);

        // 2 - Load configuration
        GameConfigurationDto configuration = GameConfigurationLoader.getConfiguration(arguments.pathFile());

        // 3 - Play game
        Game game = new ScratchRewardsGame(configuration, arguments.bettingAmount());
        GameResult result = game.play();

        // 4 - Print result
        System.out.println("\nInput: ");
        System.out.println(arguments);
        System.out.println("\nOutput: ");
        System.out.println(result);
    }

    protected static GameArguments getArguments(String[] args){
        Path configurationFilePath = null;
        double bettingAmount = 0;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case FILEPATH_CONFIG_ARGUMENT:
                    configurationFilePath = validateFilePath(args[++i]);
                    break;
                case BETTING_AMOUNT_ARGUMENT:
                    final String amount = args[++i];
                    bettingAmount = validateBettingAmount(amount);
                    break;
                default: System.out.println("Unknown argument: " + args[i]);
                    break;
            }
        }

        return new GameArguments(configurationFilePath, bettingAmount);
    }

    protected static Path validateFilePath(String filePath) {
        Path path = Paths.get(filePath);
        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException("File does not exist or is not valid: " + filePath);
        }
        return path;
    }

    protected static double validateBettingAmount(String amount) {
        if (StringUtils.isNumeric(amount)) {
            double parsedAmount = Double.parseDouble(amount);
            if (parsedAmount > 0) {
                return parsedAmount;
            }
        }
        throw new IllegalArgumentException("Invalid betting amount '" +amount + "'. Must be a positive number");
    }

    record GameArguments(Path pathFile, double bettingAmount) {
        public GameArguments {
            if (pathFile == null)
                throw new IllegalArgumentException("Argument '" + FILEPATH_CONFIG_ARGUMENT + "' cannot be null");
            if (bettingAmount <= 0)
                throw new IllegalArgumentException("Argument '" + BETTING_AMOUNT_ARGUMENT + "' must be positive");
        }
    }
}
