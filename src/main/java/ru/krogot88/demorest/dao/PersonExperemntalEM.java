package ru.krogot88.demorest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.krogot88.demorest.model.Person;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.Optional;

/**
 * User: Сашок  Date: 16.10.2019 Time: 15:19
 */
@Repository
public class PersonExperemntalEM {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void savePerson() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Person person = new Person();
        person.setLogin("test1");
        person.setPassword(passwordEncoder.encode("555"));

        em.persist(person);
        em.getTransaction().commit();
        em.close();
    }

    @Transactional
    public Optional<Person> findByLogin(String login) {
        EntityManager em = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Person> q = cb.createQuery(Person.class);
        Root<Person> root = q.from(Person.class);
        ParameterExpression<String> p = cb.parameter(String.class);
        q.select(root).where(cb.equal(root.get("login"), p));

        TypedQuery<Person> query = em.createQuery(q);
        query.setParameter(p, login);
        Person person = query.getSingleResult();
        Optional<Person> result = Optional.of(person);
        return result;
    }
}
