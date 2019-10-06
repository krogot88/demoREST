package ru.krogot88.demorest.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.krogot88.demorest.dao.WordRepository;
import ru.krogot88.demorest.model.Word;
import ru.krogot88.demorest.service.ServiceWord;


@RestController
public class RESTController {

    @Autowired
    private ServiceWord serviceWord;


    // get Random Word from db
    @RequestMapping(value = "/getword", method = RequestMethod.GET)
    public ResponseEntity<Word> getWord() {
        Word result = null;
        result = serviceWord.getNextWord();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    // REST API

    @RequestMapping(value = "/word/{id}", method = RequestMethod.GET)
    public ResponseEntity<Word> getWord(@PathVariable("id") int id) {
        Word result = serviceWord.getWordById(id);
        if(result == null)
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/word/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Word> putWord(@PathVariable("id") int id) {
        Word result = serviceWord.getWordById(id);
        if(result == null)
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/word", method = RequestMethod.POST)
    public ResponseEntity<Word> saveWord(@RequestBody Word word) {
        Word result = null;
        System.out.println(word);
        //result = serviceWord.saveNewWord(word);
        result = word;
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


}
