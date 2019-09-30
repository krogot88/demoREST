package ru.krogot88.demorest.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.krogot88.demorest.dao.WordRepository;
import ru.krogot88.demorest.model.Word;
import ru.krogot88.demorest.service.ServiceWord;


@RestController
public class RESTController {

    @Autowired
    private ServiceWord serviceWord;

    @Autowired
    private WordRepository wordRepository;

    @RequestMapping("/getword")
    public Word getWord() {
        System.out.println("in getWord() REST");
        //return serviceWord.getWord();
        Word result = null;
        Optional<Word> word = wordRepository.findById(2L);
        result = word.get();
        return result;
    }

    @RequestMapping("/saveword")
    public Word saveWord() {
        System.out.println("in getWord() REST");

        Word save = new Word("moon", "луна");
        Word result = null;
        result = wordRepository.save(save);
        return result;
    }

}
