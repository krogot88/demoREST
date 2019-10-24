package ru.krogot88.demorest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.krogot88.demorest.model.Person;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class PersonRepositoryEM implements PersonRepository{

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Transactional
    public void savePerson(Person person) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
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