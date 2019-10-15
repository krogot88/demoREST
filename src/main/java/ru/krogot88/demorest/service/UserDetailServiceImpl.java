package ru.krogot88.demorest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.krogot88.demorest.dao.PersonRepository;
import ru.krogot88.demorest.model.PersonPrincipal;


/**
 * User: Сашок  Date: 12.10.2019 Time: 19:39
 */
@Service(value = "my_UDSI")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new PersonPrincipal(personRepository.findByLogin(s).orElseThrow(() -> new UsernameNotFoundException(s)));
    }
}
