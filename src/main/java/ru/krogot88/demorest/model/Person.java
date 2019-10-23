package ru.krogot88.demorest.model;

import javax.persistence.*;
import java.util.List;

/**
 * User: Сашок  Date: 12.10.2019 Time: 18:50
 */

@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String login;

    @Column
    private String password;

    @Transient
    private String confirmPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "person_roles",
    joinColumns = @JoinColumn(name = "person_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roleList;

    public Person() {
    }

    public Person(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", roleList=" + roleList +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
