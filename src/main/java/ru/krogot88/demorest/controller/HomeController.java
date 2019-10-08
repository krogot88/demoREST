package ru.krogot88.demorest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.krogot88.demorest.dao.WordRepository;
import ru.krogot88.demorest.model.Word;
import ru.krogot88.demorest.service.ServiceWord;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class HomeController {

    @Autowired
    private ServiceWord serviceWord;

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

    //  permanent 301 redirect to "list/1"
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getListPage() {
        return "redirect:list/1";
    }

    @RequestMapping(value = "/list/{page}")
    public ModelAndView listWordsPageByPage(@PathVariable("page") int page) {
        ModelAndView modelAndView = new ModelAndView("list");
        PageRequest pageable = PageRequest.of(page - 1, 3);
        Page<Word> wordPage = serviceWord.getPaginatedWords(pageable);
        int totalPages = wordPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("activeArticleList", true); // maybe it is not used
        modelAndView.addObject("wordList", wordPage.getContent());
        return modelAndView;
    }

    @RequestMapping(value = "/getip",method = RequestMethod.GET)
    @ResponseBody
    public String getIp(HttpServletRequest httpServletRequest) {
        String addr = httpServletRequest.getRemoteAddr();
        return addr;
    }
}
