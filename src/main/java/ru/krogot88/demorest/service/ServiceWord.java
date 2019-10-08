package ru.krogot88.demorest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.krogot88.demorest.model.Word;

/**
 * User: Сашок  Date: 28.09.2019 Time: 22:47
 */
public interface ServiceWord {
    Word getNextWord();

    Word saveNewWord(Word word);

    Word getWord(String idOrName);

    Word getWordById(long id);

    Word getWordByName(String name);

    Word putWord(Word word);

    Word putWordOrUpdate(Word word);

    boolean deleteWord(String idOrName);

    Page<Word> getPaginatedArticles(Pageable pageable);
}
