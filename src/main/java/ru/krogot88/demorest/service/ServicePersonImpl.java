package ru.krogot88.demorest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.krogot88.demorest.dao.PersonRepository;
import ru.krogot88.demorest.model.Person;

import java.util.Optional;

/**
 * User: Сашок  Date: 17.10.2019 Time: 12:02
 */
@Service
public class ServicePersonImpl implements ServicePerson{

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void savePerson(Person person) {
        personRepository.savePerson(person);
    }

    @Override
    public Optional<Person> findByLogin(String login) {
        return personRepository.findByLogin(login);
    }
}
