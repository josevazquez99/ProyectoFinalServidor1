package com.vedruna.proyectoFinalServidor1.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UrlValidator implements ConstraintValidator<ValidUrl, String> {

    private static final String URL_REGEX = "^(http|https)://[a-zA-Z0-9-_.]+(?:\\.[a-zA-Z]{2,})+(?:/[\\w-._~:/?#[\\]@!$&'()*+,;=.]+)*$";

    /**
     * Initializes the validator in preparation for {@link #isValid(Object, ConstraintValidatorContext) }
     * to be called.
     * <p>
     * The constraint annotation for the constraint being validated, eg {@link NotNull}
     *
     * @param constraintAnnotation annotation instance containing the annotation's attributes
     */
    @Override
    public void initialize(ValidUrl constraintAnnotation) {
    }


    /**
     * Evaluates the url format.
     *
     * @param value   the url to be validated
     * @param context context in which the constraint is evaluated
     * @return false if the url is not valid, true otherwise
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return Pattern.matches(URL_REGEX, value);
    }
}
