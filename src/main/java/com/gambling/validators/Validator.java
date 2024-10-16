package com.gambling.validators;

import java.util.Set;

public abstract class Validator {

    public abstract Set<String> matchSymbols(String[][] matrix);
}
