package ru.krogot88.demorest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.krogot88.demorest.dto.WordGameDTO;
import ru.krogot88.demorest.model.Word;
import ru.krogot88.demorest.dto.ResponseWrapper;

import java.security.Principal;
import java.util.Map;

/**
 * User: Сашок  Date: 28.09.2019 Time: 22:47
 */
public interface ServiceWord {

    ResponseWrapper<Word> checkWordTranslate(Word word, Principal principal);

    ResponseWrapper<WordGameDTO> getRandomWordGameDTO(Long variants);

    ResponseWrapper<Word> getRandomWord();

    ResponseWrapper<Word> getWordById(Long id);

    ResponseWrapper<Word> getWordByName(String name);

    ResponseWrapper<Word> saveNewWord(Word word);

    ResponseWrapper<Word> updateWordById(Word word, Long id);

    ResponseWrapper<Word> patchWordByName(Map<String,String> json, String name);

    ResponseWrapper<Word> deleteWordById(Long id);

    ResponseWrapper<Word> deleteWordByName(String name);

    Page<Word> getPaginatedWords(Pageable pageable);
}
