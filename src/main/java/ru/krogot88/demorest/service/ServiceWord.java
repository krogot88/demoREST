package ru.krogot88.demorest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.krogot88.demorest.model.Word;
import ru.krogot88.demorest.dto.WordBox;

import java.util.Map;

/**
 * User: Сашок  Date: 28.09.2019 Time: 22:47
 */
public interface ServiceWord {
    Word getRandomWord();

    WordBox getWordById(Long id);

    WordBox getWordByName(String name);

    WordBox saveNewWord(Word word);

    WordBox updateWordById(Word word, Long id);

    WordBox patchWordByName(Map<String,String> json, String name);

    WordBox deleteWordById(Long id);

    WordBox deleteWordByName(String name);

    Page<Word> getPaginatedWords(Pageable pageable);
}
