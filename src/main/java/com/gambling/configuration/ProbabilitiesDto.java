package com.gambling.configuration;

import java.util.List;

public record ProbabilitiesDto(
        List<StandardSymbolProbabilityDto> standardSymbols,
        BonusSymbolsDto bonusSymbols
) {}
