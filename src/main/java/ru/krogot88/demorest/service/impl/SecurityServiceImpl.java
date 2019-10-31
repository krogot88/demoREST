package ru.krogot88.demorest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.krogot88.demorest.service.SecurityService;

/**
 * User: Сашок  Date: 31.10.2019 Time: 15:52
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("UserDetailServiceImpl")
    private UserDetailsService userDetailsService;

    @Override
    public String findLoggedInLogin() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails)
            return ((UserDetails) userDetails).getUsername();
        return null;
    }

    @Override
    public void autoLogin(String login, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());

        //authenticationManager.authenticate(authenticationToken); -- throw Bad Credentials
        //if (authenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //}

    }
}
