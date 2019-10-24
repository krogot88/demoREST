package ru.krogot88.demorest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * User: Сашок  Date: 13.10.2019 Time: 16:28
 */
public class EnglishValidator implements ConstraintValidator<English,String> {
    @Override
    public void initialize(English constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return ((!value.equals(""))
                && (value != null)
                && (value.matches("^[a-zA-Z]*$")));
    }
}
