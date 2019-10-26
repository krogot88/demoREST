package ru.krogot88.demorest.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.krogot88.demorest.validator.English;
import ru.krogot88.demorest.validator.Russian;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Table(name = "words")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Size(min = 2, max = 50, message = "{word.too.short}")
    @English(message = "{word.not.english}")
    private String name;

    @Column(name = "translate")
    @Russian(message = "{word.not.russian}")
    @NotBlank(message = "{translate.not.empty}")
    private String translate;

    @ManyToMany(mappedBy = "wordSet")
    @JsonIgnore   //  to avoid  infinite loop personList -> roleList ->personList ...   When select word and it exists in table person_progress
    private List<Person> personList;

    public Word() {
        super();
    }

    public Word(String name, String translate) {
        super();
        this.name = name;
        this.translate = translate;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", translate='" + translate + '\'' +
                '}';
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslate() {
        return translate;
    }
    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}