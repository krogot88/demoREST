package ru.krogot88.demorest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.krogot88.demorest.dao.PersonRepository;
import ru.krogot88.demorest.dao.RoleRepository;
import ru.krogot88.demorest.model.Person;
import ru.krogot88.demorest.model.Role;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * User: Сашок  Date: 17.10.2019 Time: 12:02
 */
@Service
public class ServicePersonImpl implements ServicePerson{

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void savePerson(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        List<Role> roles = new LinkedList<>();
        roles.add(roleRepository.getOne(2L));
        person.setRoleList(roles);
        personRepository.savePerson(person);
    }

    @Override
    public Optional<Person> findByLogin(String login) {
        return personRepository.findByLogin(login);
    }
}
