package ru.krogot88.demorest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.krogot88.demorest.dto.WordFourDTO;
import ru.krogot88.demorest.model.Word;
import ru.krogot88.demorest.service.ServiceWord;
import ru.krogot88.demorest.dto.ResponseWrapper;

import javax.validation.constraints.Positive;
import java.util.Map;

@RestController
@Validated
public class RESTController {

    @Autowired
    private ServiceWord serviceWord;

    @GetMapping(value = "/word/random/game")
    public ResponseEntity<WordFourDTO> getRandomWordFourDTO() {
        ResponseWrapper<WordFourDTO> responseWrapper = serviceWord.getRandomWordFourDTO();
        return new ResponseEntity<>(responseWrapper.getEntity(), responseWrapper.getHttpStatus());
    }

    @GetMapping(value = "/word/random")
    public ResponseEntity<Word> getRandomWord() {
        ResponseWrapper<Word> responseWrapper = serviceWord.getRandomWord();
        return new ResponseEntity<>(responseWrapper.getEntity(), responseWrapper.getHttpStatus());
    }

    @GetMapping(value = "/word/id/{id}")
    public ResponseEntity<Word> getWordById(@Positive @PathVariable("id") Long id) {
        ResponseWrapper<Word> responseWrapper = serviceWord.getWordById(id);
        return new ResponseEntity<>(responseWrapper.getEntity(), responseWrapper.getHttpStatus());
    }

    @GetMapping(value = "/word/name/{name}")
    public ResponseEntity<Word> getWordByName(@PathVariable("name") String name) {
        ResponseWrapper<Word> responseWrapper = serviceWord.getWordByName(name);
        return new ResponseEntity<>(responseWrapper.getEntity(), responseWrapper.getHttpStatus());
    }

    @PostMapping(value = "/word")
    public ResponseEntity<Word> saveNewWord(@Validated @RequestBody Word word) {
        ResponseWrapper<Word> responseWrapper = serviceWord.saveNewWord(word);
        return new ResponseEntity<>(responseWrapper.getEntity(), responseWrapper.getHttpStatus());
    }

    @PutMapping(value = "/word/id/{id}")
    public ResponseEntity<Word> putWordById(@Positive @PathVariable("id") Long id, @Validated @RequestBody Word word) {
        ResponseWrapper<Word> responseWrapper = serviceWord.updateWordById(word, id);
        return new ResponseEntity<>(responseWrapper.getEntity(), responseWrapper.getHttpStatus());
    }

    @PatchMapping(value = "/word/name/{name}")
    public ResponseEntity<Word> patchWordByName(@PathVariable("name") String name, @RequestBody Map<String, String> json) {
        ResponseWrapper<Word> responseWrapper = serviceWord.patchWordByName(json, name);
        return new ResponseEntity<>(responseWrapper.getEntity(), responseWrapper.getHttpStatus());
    }

    @DeleteMapping(value = "/word/id/{id}")
    public ResponseEntity<Word> deleteWordById(@Positive @PathVariable("id") Long id) {
        ResponseWrapper<Word> responseWrapper = serviceWord.deleteWordById(id);
        return new ResponseEntity<>(responseWrapper.getEntity(), responseWrapper.getHttpStatus());
    }

    @DeleteMapping(value = "/word/name/{name}")
    public ResponseEntity<Word> deleteWordByName(@PathVariable("name") String name) {
        ResponseWrapper<Word> responseWrapper = serviceWord.deleteWordByName(name);
        return new ResponseEntity<>(responseWrapper.getEntity(), responseWrapper.getHttpStatus());
    }


}
