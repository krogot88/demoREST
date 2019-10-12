package ru.krogot88.demorest.model;

import javax.persistence.*;
import java.util.List;

/**
 * User: Сашок  Date: 12.10.2019 Time: 18:55
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String role_name;

    @ManyToMany(mappedBy = "role_list")
    private List<Person> person_list;

    public Role() {
    }

    public Role(Long id, String role_name) {
        this.id = id;
        this.role_name = role_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public List<Person> getPerson_list() {
        return person_list;
    }

    public void setPerson_list(List<Person> person_list) {
        this.person_list = person_list;
    }
}
