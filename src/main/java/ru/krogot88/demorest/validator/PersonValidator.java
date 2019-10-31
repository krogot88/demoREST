package ru.krogot88.demorest.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.krogot88.demorest.dao.PersonRepository;
import ru.krogot88.demorest.model.Person;

/**
 * User: Сашок  Date: 31.10.2019 Time: 14:10
 */
@Component
public class PersonValidator implements Validator {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"login","loginHasWhiteSpacesOrEmpty");
        if(person.getLogin().contains(" ")) {
            errors.rejectValue("login","loginHasWhiteSpacesOrEmpty");
        }
        if (personRepository.findByLogin(person.getLogin()).orElse(null) != null) {
            errors.rejectValue("login","thisLoginAlreadyExists");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","passwordRequired");

        if (!person.getConfirmPassword().equals(person.getPassword())) {
            errors.rejectValue("confirmPassword","passwordAndConfirmPasswordNotEquals");
        }
    }
}
