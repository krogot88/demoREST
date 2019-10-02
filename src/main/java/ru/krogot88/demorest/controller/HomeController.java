package ru.krogot88.demorest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.krogot88.demorest.dao.WordRepository;
import ru.krogot88.demorest.model.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private WordRepository wordRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndexPage() {
        return "index";
    }

    @RequestMapping(value = "/play", method = RequestMethod.GET)
    public String getPlayPage() {
        return "play";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getListPage(Model model) {
        List<Word> list = wordRepository.findAll();
        model.addAttribute("wordList",list);
        return "list";
    }
}
