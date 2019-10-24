package ru.krogot88.demorest.service;

import ru.krogot88.demorest.model.Person;

import java.util.Optional;

/**
 * User: Сашок  Date: 17.10.2019 Time: 12:02
 */
public interface ServicePerson {

    void savePerson(Person person);

    Optional<Person> findByLogin(String login);
}
