package com.gambling.games;

import com.gambling.configuration.*;
import com.gambling.matrices.MatrixGenerator;
import com.gambling.matrices.RandomMatrix;
import com.gambling.validators.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScratchRewardsGameTest {

    private GameConfigurationDto mockConfiguration;
    private RandomMatrix mockRandomMatrix;
    private SymbolDto mockSymbolDto;
    private WinCombinationDto mockWinCombinationDto;
    private ScratchRewardsGame game;
    private double bettingAmount = 10.0;

    @BeforeEach
    void setUp() {
        mockConfiguration = mock(GameConfigurationDto.class);
        mockRandomMatrix = mock(RandomMatrix.class);
        mockSymbolDto = mock(SymbolDto.class);
        mockWinCombinationDto = mock(WinCombinationDto.class);
        game = new ScratchRewardsGame(mockConfiguration, bettingAmount);
    }

    @Test
    void testApplyBonusMultiplyReward() {
        when(mockSymbolDto.impact()).thenReturn("multiply_reward");
        when(mockSymbolDto.rewardMultiplier()).thenReturn(2.0);

        double result = game.applyBonus(mockSymbolDto, 100.0);

        assertEquals(200.0, result);
    }

    @Test
    void testApplyBonusExtraBonus() {
        when(mockSymbolDto.impact()).thenReturn("extra_bonus");
        when(mockSymbolDto.extra()).thenReturn((int) 50.0);

        double result = game.applyBonus(mockSymbolDto, 100.0);

        assertEquals(150.0, result);
    }

    @Test
    void testApplyBonusMiss() {
        when(mockSymbolDto.impact()).thenReturn("miss");

        double result = game.applyBonus(mockSymbolDto, 100.0);

        assertEquals(0.0, result);
    }

    @Test
    void testReduceGroupCombinations() {
        Map<String, WinCombinationDto> winCombinations = new HashMap<>();
        when(mockWinCombinationDto.rewardMultiplier()).thenReturn(2.0);
        when(mockWinCombinationDto.group()).thenReturn("group1");

        WinCombinationDto otherCombination = mock(WinCombinationDto.class);
        when(otherCombination.rewardMultiplier()).thenReturn(3.0);
        when(otherCombination.group()).thenReturn("group1");

        winCombinations.put("combo1", mockWinCombinationDto);
        winCombinations.put("combo2", otherCombination);

        when(mockConfiguration.winCombinations()).thenReturn(winCombinations);

        Set<String> result = game.reduceGroupCombinations("A", new HashSet<>(Arrays.asList("combo1", "combo2")));

        assertTrue(result.contains("combo2"));
        assertFalse(result.contains("combo1"));
    }
}
