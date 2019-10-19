package ru.krogot88.demorest.model;


import ru.krogot88.demorest.validator.English;
import ru.krogot88.demorest.validator.Russian;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "words")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Size(min = 2, max = 50, message = "{word.too.short}")
    @English(message = "must be only Eng symbols")
    private String name;

    @Column(name = "translate")
    @Russian(message = "must be only Rus symbols")
    @NotBlank(message = "must be not empty")
    private String translate;

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
}