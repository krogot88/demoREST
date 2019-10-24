package ru.krogot88.demorest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.krogot88.demorest.dao.WordRepository;
import ru.krogot88.demorest.dto.WordGameDTO;
import ru.krogot88.demorest.model.Word;
import ru.krogot88.demorest.dto.ResponseWrapper;

import java.util.*;


/**
 * User: Сашок  Date: 28.09.2019 Time: 22:47
 */
@Service
public class ServiceWordsImpl implements ServiceWord {
    private static final long MIN_ROW_COUNTS_IN_TABLE = 1L;

    @Autowired
    private WordRepository wordRepository;

    @Override
    public ResponseWrapper<WordGameDTO> getRandomWordGameDTO(Long variants) {
        Long countRawsInTable = wordRepository.count();
        if(countRawsInTable < variants)
            return new ResponseWrapper<>(HttpStatus.NO_CONTENT);
        WordGameDTO resultDTO = new WordGameDTO();
        Word word = wordRepository.getRandomWord();
        List<String> list = new ArrayList<>();
        list.add(word.getTranslate());
        while (list.size() != variants) {
            Word temp = wordRepository.getRandomWord();
            if(!list.contains(temp.getTranslate())) {
                list.add(temp.getTranslate());
            }
        }
        Collections.shuffle(list);
        resultDTO.setName(word.getName());
        resultDTO.setTranslates(list);
        return new ResponseWrapper<>(resultDTO,HttpStatus.OK);
    }


    @Override
    public ResponseWrapper<Word> getRandomWord() {
        Long countRawsInTable = wordRepository.count();
        if(countRawsInTable < MIN_ROW_COUNTS_IN_TABLE)
            return new ResponseWrapper<>(HttpStatus.NO_CONTENT);
        return new ResponseWrapper<>(wordRepository.getRandomWord(),HttpStatus.OK);
    }

    @Override
    public ResponseWrapper<Word> getWordById(Long id) {
        Word word = wordRepository.findById(id).orElse(null);
        if (word == null)
            return new ResponseWrapper<>(HttpStatus.NOT_FOUND);
        return new ResponseWrapper<>(word, HttpStatus.OK);
    }

    @Override
    public ResponseWrapper<Word> getWordByName(String name) {
        Word word = wordRepository.findByName(name).orElse(null);
        if (word == null)
            return new ResponseWrapper<>(HttpStatus.NOT_FOUND);
        return new ResponseWrapper<>(word, HttpStatus.OK);
    }

    @Override
    public ResponseWrapper<Word> saveNewWord(Word word) {
        word.setId(null);
        if (wordRepository.findByName(word.getName()).isPresent())
            return new ResponseWrapper<>(HttpStatus.CONFLICT);
        return new ResponseWrapper<>(wordRepository.save(word), HttpStatus.CREATED);
    }

    @Override
    public ResponseWrapper<Word> updateWordById(Word word, Long id) {
        if (!wordRepository.findById(id).isPresent())
            return new ResponseWrapper<>(HttpStatus.NOT_FOUND);
        if (wordRepository.findByName(word.getName()).isPresent() &&
                wordRepository.findByName(word.getName()).get().getId() != id)
            return new ResponseWrapper<>(HttpStatus.CONFLICT);
        word.setId(id);
        return new ResponseWrapper<>(wordRepository.save(word), HttpStatus.OK);
    }

    @Override
    public ResponseWrapper<Word> patchWordByName(Map<String, String> json, String name) {
        Word word = wordRepository.findByName(name).orElse(null);
        if (word == null)
            return new ResponseWrapper<>(HttpStatus.NOT_FOUND);
        if(!json.containsKey("translate"))
            return new ResponseWrapper<>(HttpStatus.BAD_REQUEST);
        word.setTranslate(json.get("translate"));
        return new ResponseWrapper<>(wordRepository.save(word), HttpStatus.OK);
    }

    @Override
    public ResponseWrapper<Word> deleteWordById(Long id) {
        if (!wordRepository.findById(id).isPresent())
            return new ResponseWrapper<>(HttpStatus.NOT_FOUND);
        wordRepository.deleteById(id);
        return new ResponseWrapper<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseWrapper<Word> deleteWordByName(String name) {
        Word word = wordRepository.findByName(name).orElse(null);
        if (word == null)
            return new ResponseWrapper<>(HttpStatus.NOT_FOUND);
        wordRepository.delete(word);
        return new ResponseWrapper<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public Page<Word> getPaginatedWords(Pageable pageable) {
        return wordRepository.findAll(pageable);
    }
}
