package ru.krogot88.demorest.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.krogot88.demorest.dao.WordRepository;
import ru.krogot88.demorest.model.Word;
import ru.krogot88.demorest.service.ServiceWord;


@RestController
public class RESTController {

    @Autowired
    private ServiceWord serviceWord;



    @RequestMapping("/getword")
    public ResponseEntity<Word> getWord() {
        Word result = null;
        result = serviceWord.getNextWord();
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
