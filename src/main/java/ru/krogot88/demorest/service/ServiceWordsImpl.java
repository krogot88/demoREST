package ru.krogot88.demorest.service;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.krogot88.demorest.dao.WordRepository;
import ru.krogot88.demorest.model.Word;
import ru.krogot88.demorest.util.WordBox;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * User: Сашок  Date: 28.09.2019 Time: 22:47
 */
@Service
public class ServiceWordsImpl implements ServiceWord {

    @Autowired
    private WordRepository wordRepository;

    private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    @Override
    public Word getRandomWord() {
        Word result = wordRepository.getRandomWord();
        return result;
    }

    @Override
    public WordBox getWordById(Long id) {
        Word word = wordRepository.findById(id).orElse(null);
        if (word == null)
            return new WordBox(HttpStatus.NOT_FOUND);
        return new WordBox(word, HttpStatus.OK);
    }

    @Override
    public WordBox getWordByName(String name) {
        try {
            Word word = wordRepository.findByName(name).get();
            return new WordBox(word, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new WordBox(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public WordBox saveNewWord(Word word) {
        word.setId(null);
        if (wordRepository.findByName(word.getName()).isPresent())
            return new WordBox(HttpStatus.CONFLICT);
        return new WordBox(wordRepository.save(word), HttpStatus.CREATED);
    }

    @Override
    public WordBox updateWordById(Word word, Long id) {
        if (!wordRepository.findById(id).isPresent())
            return new WordBox(HttpStatus.NOT_FOUND);
        if (wordRepository.findByName(word.getName()).isPresent() &&
                wordRepository.findByName(word.getName()).get().getId() != id)
            return new WordBox(HttpStatus.CONFLICT);
        word.setId(id);
        return new WordBox(wordRepository.save(word), HttpStatus.OK);
    }

    @Override
    public WordBox patchWordByName(Map<String, String> json, String name) {
        Word word = wordRepository.findByName(name).orElse(null);
        if (word == null)
            return new WordBox(HttpStatus.NOT_FOUND);
        if(!json.containsKey("translate"))
            return new WordBox(HttpStatus.BAD_REQUEST);
        word.setTranslate(json.get("translate"));
        return new WordBox(wordRepository.save(word), HttpStatus.OK);
    }

    @Override
    public WordBox deleteWordById(Long id) {
        if (!wordRepository.findById(id).isPresent())
            return new WordBox(HttpStatus.NOT_FOUND);
        wordRepository.deleteById(id);
        return new WordBox(HttpStatus.NO_CONTENT);
    }

    @Override
    public WordBox deleteWordByName(String name) {
        Word word = wordRepository.findByName(name).orElse(null);
        if (word == null)
            return new WordBox(HttpStatus.NOT_FOUND);
        wordRepository.delete(word);
        return new WordBox(HttpStatus.NO_CONTENT);
    }

    @Override
    public Page<Word> getPaginatedWords(Pageable pageable) {
        return wordRepository.findAll(pageable);
    }
}
