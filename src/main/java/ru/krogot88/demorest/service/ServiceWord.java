package ru.krogot88.demorest.service;

import ru.krogot88.demorest.model.Word;

/**
 * User: Сашок  Date: 28.09.2019 Time: 22:47
 */
public interface ServiceWord {
    Word getNextWord();

    Word saveNewWord(Word word);

    Word getWordById(long id);
}
