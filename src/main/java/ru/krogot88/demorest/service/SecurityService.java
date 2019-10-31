package ru.krogot88.demorest.service;

/**
 * User: Сашок  Date: 31.10.2019 Time: 15:50
 */
public interface SecurityService {

    String findLoggedInLogin();

    void autoLogin(String login, String password);
}
