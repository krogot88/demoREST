package ru.krogot88.demorest.service;

import org.springframework.stereotype.Service;
import ru.krogot88.demorest.model.Word;

/**
 * User: Сашок  Date: 28.09.2019 Time: 22:47
 */
@Service
public class ServiceWordsImpl implements ServiceWord {
    @Override
    public Word getWord() {
        return new Word("home","дом");
    }
}
