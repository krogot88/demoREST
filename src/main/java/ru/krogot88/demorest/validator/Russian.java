package ru.krogot88.demorest.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * User: Сашок  Date: 13.10.2019 Time: 16:23
 */
@Documented
@Constraint(validatedBy = RussianValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Russian {
    String message() default "{word.not.english}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}