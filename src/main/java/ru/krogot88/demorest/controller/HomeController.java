package ru.krogot88.demorest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.krogot88.demorest.dao.WordRepository;
import ru.krogot88.demorest.model.Word;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

    @RequestMapping(value = "/loadnew", method = RequestMethod.GET)
    public String getLoadNewPage() {
        return "loadnew";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getListPage(Model model) {
        List<Word> list = wordRepository.findAll();
        model.addAttribute("wordList",list);
        return "list";
    }

    @RequestMapping(value = "/getip",method = RequestMethod.GET)
    @ResponseBody
    public String getIp(HttpServletRequest httpServletRequest) {
        String addr = httpServletRequest.getRemoteAddr();
        return addr;
    }
}
