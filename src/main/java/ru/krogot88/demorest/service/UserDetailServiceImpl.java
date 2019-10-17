package ru.krogot88.demorest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.krogot88.demorest.dao.PersonRepositoryEM;
import ru.krogot88.demorest.model.PersonPrincipal;


/**
 * User: Сашок  Date: 12.10.2019 Time: 19:39
 */
@Service(value = "my_UDSI")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PersonRepositoryEM personRepositoryEM;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new PersonPrincipal(personRepositoryEM.findByLogin(s).orElseThrow(() -> new UsernameNotFoundException(s)));
    }
}
