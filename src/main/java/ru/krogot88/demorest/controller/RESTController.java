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
        Word result = serviceWord.getNextWord();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    // REST API

    // GET by id or name
    @RequestMapping(value = "/word/{idOrName}", method = RequestMethod.GET)
    public ResponseEntity<Word> getWord(@PathVariable("idOrName") String idOrName ) {
        Word result = serviceWord.getWord(idOrName);
        if(result == null)
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // POST ,  the id  will be ignored anyway. Try insert new row in table and then get new id
    @RequestMapping(value = "/word", method = RequestMethod.POST)
    public ResponseEntity<Word> saveWord(@RequestBody Word word) {
        Word result = serviceWord.saveNewWord(word);
        if(result == null)
            return new ResponseEntity<>( HttpStatus.CONFLICT);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // PUT , try put new name and/or translate to specified  id. Maybe it is better to rename PATCH...
    @RequestMapping(value = "/word/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Word> putWord(@PathVariable("id") Long id, @RequestBody Word word) {
        if(id <= 0)
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        word.setId(id);

        Word alreadyExistOnOverId = serviceWord.getWord(word.getName());
        if(alreadyExistOnOverId.getId() != word.getId())
            return new ResponseEntity<>( HttpStatus.CONFLICT);

        Word result = serviceWord.putWord(word);
        if(result == null)
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // PUT , try put new word or update if it exists. Id is ignored
    @RequestMapping(value = "/word", method = RequestMethod.PUT)
    public ResponseEntity<Word> putWordOrUpdate(@RequestBody Word word) {
        word.setId(null);
        Word result = serviceWord.putWordOrUpdate(word);
        if(result == null)
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // DELETE , try delete word if it exists.
    @RequestMapping(value = "/word/{idOrName}", method = RequestMethod.DELETE)
    public ResponseEntity<Word> deleteWord(@PathVariable("idOrName") String idOrName) {
        Boolean success = serviceWord.deleteWord(idOrName);
        if(!success)
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
