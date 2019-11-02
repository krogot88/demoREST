package ru.krogot88.demorest.util;

import java.util.List;

/**
 * User: Сашок  Date: 02.11.2019 Time: 18:17
 */
public class WordYandex {
    public Object head;
    public List<SubWord> def;

    @Override
    public String toString() {
        return "WordYandex{" +
                "def=" + def +
                '}';
    }
}

class SubWord {
    public List<SubSubWord> tr;

    @Override
    public String toString() {
        return "SubWord{" +
                "tr=" + tr +
                '}';
    }
}

class SubSubWord {
    public String text;

    @Override
    public String toString() {
        return "SubSubWord{" +
                "text='" + text + '\'' +
                '}' + "\n";
    }
}
