package com.vedruna.proyectoFinalServidor1.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UrlValidator implements ConstraintValidator<ValidUrl, String> {

    private static final String URL_REGEX = "^(http|https)://[a-zA-Z0-9-_.]+(?:\\.[a-zA-Z]{2,})+(?:/[\\w-._~:/?#[\\]@!$&'()*+,;=.]+)*$";

    @Override
    public void initialize(ValidUrl constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return Pattern.matches(URL_REGEX, value);
    }
}
