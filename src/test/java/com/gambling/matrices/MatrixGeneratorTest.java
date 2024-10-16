package com.gambling.matrices;

import com.gambling.configuration.BonusSymbolsDto;
import com.gambling.configuration.GameConfigurationDto;
import com.gambling.configuration.ProbabilitiesDto;
import com.gambling.configuration.StandardSymbolProbabilityDto;
import com.gambling.exceptions.ScratchGameException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MatrixGeneratorTest {

    @Test
    void generateRandomMatrix_shouldGenerateMatrixWithGivenProbabilities() {
        GameConfigurationDto config = Mockito.mock(GameConfigurationDto.class);
        ProbabilitiesDto probabilities = Mockito.mock(ProbabilitiesDto.class);

        List<StandardSymbolProbabilityDto> standardSymbols = List.of(
                new StandardSymbolProbabilityDto(0, 0, Map.of("A", 70, "B", 30)),
                new StandardSymbolProbabilityDto(0, 1, Map.of("A", 50, "C", 50)),
                new StandardSymbolProbabilityDto(1, 0, Map.of("B", 60, "C", 40)),
                new StandardSymbolProbabilityDto(1, 1, Map.of("A", 10, "B", 90))
        );

        Mockito.when(config.rows()).thenReturn(2);
        Mockito.when(config.columns()).thenReturn(2);
        Mockito.when(probabilities.standardSymbols()).thenReturn(standardSymbols);
        Mockito.when(config.probabilities()).thenReturn(probabilities);

        RandomMatrix randomMatrix = MatrixGenerator.generateRandomMatrix(config);

        assertNotNull(randomMatrix.matrix());
        assertEquals(2, randomMatrix.matrix().length);
        assertEquals(2, randomMatrix.matrix()[0].length);
    }

    @Test
    void generateRandomMatrix_shouldInsertBonusSymbol() {
        GameConfigurationDto config = Mockito.mock(GameConfigurationDto.class);
        ProbabilitiesDto probabilities = Mockito.mock(ProbabilitiesDto.class);
        BonusSymbolsDto bonusSymbolsDto = Mockito.mock(BonusSymbolsDto.class);

        List<StandardSymbolProbabilityDto> standardSymbols = List.of(
                new StandardSymbolProbabilityDto(0, 0, Map.of("A", 70, "B", 30)),
                new StandardSymbolProbabilityDto(0, 1, Map.of("A", 50, "C", 50)),
                new StandardSymbolProbabilityDto(1, 0, Map.of("B", 60, "C", 40)),
                new StandardSymbolProbabilityDto(1, 1, Map.of("A", 10, "B", 90))
        );

        Map<String, Integer> bonusSymbols = Map.of("BONUS", 100);

        Mockito.when(config.rows()).thenReturn(2);
        Mockito.when(config.columns()).thenReturn(2);
        Mockito.when(probabilities.standardSymbols()).thenReturn(standardSymbols);
        Mockito.when(probabilities.bonusSymbols()).thenReturn(bonusSymbolsDto);
        Mockito.when(bonusSymbolsDto.symbols()).thenReturn(bonusSymbols);
        Mockito.when(config.probabilities()).thenReturn(probabilities);

        RandomMatrix randomMatrix = MatrixGenerator.generateRandomMatrix(config);

        assertNotNull(randomMatrix.bonus());
        assertTrue(List.of("BONUS").contains(randomMatrix.bonus()));
    }


    @Test
    void generateRandomMatrix_shouldThrowExceptionWhenProbabilityIsMissing() {
        GameConfigurationDto config = Mockito.mock(GameConfigurationDto.class);
        ProbabilitiesDto probabilities = Mockito.mock(ProbabilitiesDto.class);

        List<StandardSymbolProbabilityDto> standardSymbols = List.of(
                new StandardSymbolProbabilityDto(0, 0, Map.of("A", 70, "B", 30)),
                new StandardSymbolProbabilityDto(0, 1, Map.of("A", 50, "C", 50)),
                new StandardSymbolProbabilityDto(1, 0, Map.of("B", 60, "C", 40))
        );

        Mockito.when(config.rows()).thenReturn(2);
        Mockito.when(config.columns()).thenReturn(2);
        Mockito.when(probabilities.standardSymbols()).thenReturn(standardSymbols);
        Mockito.when(config.probabilities()).thenReturn(probabilities);

        assertThrows(ScratchGameException.class, () -> MatrixGenerator.generateRandomMatrix(config));
    }


}
