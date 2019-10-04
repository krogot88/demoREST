package ru.krogot88.demorest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.krogot88.demorest.dao.WordRepository;
import ru.krogot88.demorest.model.Word;

/**
 * User: Сашок  Date: 28.09.2019 Time: 22:47
 */
@Service
public class ServiceWordsImpl implements ServiceWord {

    @Autowired
    private WordRepository wordRepository;

    @Override
    public Word getNextWord() {
        Word result = null;
        result = wordRepository.findNextWord();
        return result;
    }

    @Override
    public Word saveNewWord(Word word) {
        Word toSave = word;
        return wordRepository.save(toSave);
    }
}
