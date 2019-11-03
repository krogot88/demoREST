package ru.krogot88.demorest.util.yandex;

import java.util.List;

/**
 * User: Сашок  Date: 02.11.2019 Time: 18:17
 */
public class WordYandexJSON {
    public Object head;
    public List<PartOfSpeech> def;

    @Override
    public String toString() {
        return "WordYandex{" +
                "def=" + def +
                '}';
    }
}

