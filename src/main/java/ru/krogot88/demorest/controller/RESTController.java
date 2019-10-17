package ru.krogot88.demorest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.krogot88.demorest.model.Word;
import ru.krogot88.demorest.service.ServiceWord;
import ru.krogot88.demorest.dto.WordBox;

import javax.validation.constraints.Positive;
import java.util.Map;

@RestController
@Validated
public class RESTController {

    @Autowired
    private ServiceWord serviceWord;

    @GetMapping(value = "/word/random")
    public ResponseEntity<Word> getRandomWord() {
        Word result = serviceWord.getRandomWord();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/word/id/{id}")
    public ResponseEntity<Word> getWordById(@Positive @PathVariable("id") Long id) {
        WordBox wordBox = serviceWord.getWordById(id);
        return new ResponseEntity<>(wordBox.getWord(), wordBox.getHttpStatus());
    }

    @GetMapping(value = "/word/name/{name}")
    public ResponseEntity<Word> getWordByName(@PathVariable("name") String name) {
        WordBox wordBox = serviceWord.getWordByName(name);
        return new ResponseEntity<>(wordBox.getWord(), wordBox.getHttpStatus());
    }

    @PostMapping(value = "/word")
    public ResponseEntity<Word> saveNewWord(@Validated @RequestBody Word word) {
        WordBox wordBox = serviceWord.saveNewWord(word);
        return new ResponseEntity<>(wordBox.getWord(), wordBox.getHttpStatus());
    }

    @PutMapping(value = "/word/id/{id}")
    public ResponseEntity<Word> putWordById(@Positive @PathVariable("id") Long id, @Validated @RequestBody Word word) {
        WordBox wordBox = serviceWord.updateWordById(word, id);
        return new ResponseEntity<>(wordBox.getWord(), wordBox.getHttpStatus());
    }

    @PatchMapping(value = "/word/name/{name}")
    public ResponseEntity<Word> patchWordByName(@PathVariable("name") String name, @RequestBody Map<String, String> json) {
        WordBox wordBox = serviceWord.patchWordByName(json, name);
        return new ResponseEntity<>(wordBox.getWord(), wordBox.getHttpStatus());
    }

    @DeleteMapping(value = "/word/id/{id}")
    public ResponseEntity<Word> deleteWordById(@Positive @PathVariable("id") Long id) {
        WordBox wordBox = serviceWord.deleteWordById(id);
        return new ResponseEntity<>(wordBox.getWord(), wordBox.getHttpStatus());
    }

    @DeleteMapping(value = "/word/name/{name}")
    public ResponseEntity<Word> deleteWordByName(@PathVariable("name") String name) {
        WordBox wordBox = serviceWord.deleteWordByName(name);
        return new ResponseEntity<>(wordBox.getWord(), wordBox.getHttpStatus());
    }
}
