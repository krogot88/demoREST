package ru.krogot88.demorest.dto;

import java.util.List;


public class WordGameDTO {
    private String name;
    private List<String> translates;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTranslates() {
        return translates;
    }

    public void setTranslates(List<String> translates) {
        this.translates = translates;
    }
}
