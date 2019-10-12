package ru.krogot88.demorest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krogot88.demorest.model.Person;

import java.util.Optional;

/**
 * User: Сашок  Date: 12.10.2019 Time: 19:43
 */
public interface PersonRepository extends JpaRepository<Person,Long> {

    Optional<Person> findByLogin(String login);
}
