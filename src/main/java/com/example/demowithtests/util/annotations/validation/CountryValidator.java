package com.example.demowithtests.util.annotations.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class CountryValidator implements ConstraintValidator<Country, String> {
    private final Set<String> countries;

    public CountryValidator() {
        this.countries = new HashSet<>(Arrays.asList(Locale.getISOCountries()));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // null values are validated by @NotNull
        }
        if (value.length() != 2 || !value.matches("[A-Z]{2}")) {
            return false;
        }
        return countries.contains(value);
    }
}