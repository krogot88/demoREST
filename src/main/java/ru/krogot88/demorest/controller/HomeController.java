package ru.krogot88.demorest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.krogot88.demorest.model.Word;
import ru.krogot88.demorest.service.ServiceWord;
import ru.krogot88.demorest.util.Utils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class HomeController {

    @Autowired
    private ServiceWord serviceWord;

    @GetMapping(value = "/")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping(value = "/play")
    public String getPlayPage() {
        return "play";
    }

    @GetMapping(value = "/loadnew")
    public String getLoadNewPage() {
        return "loadnew";
    }

    @GetMapping(value = "/documentation")
    public String getDocumentationPage() {
        return "documentation";
    }

    @GetMapping(value = "/list")
    public String getListPage() {
        return "redirect:list/1";
    }

    @RequestMapping(value = "/list/{page}")
    public ModelAndView listWordsPageByPage(@PathVariable("page") int page) {
        ModelAndView modelAndView = new ModelAndView("list");
        PageRequest pageable = PageRequest.of(page - 1, 25);
        Page<Word> wordPage = serviceWord.getPaginatedWords(pageable);
        int totalPages = wordPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = Utils.createConsecutiveList(totalPages);
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("activeArticleList", true);
        modelAndView.addObject("wordList", wordPage.getContent());
        modelAndView.addObject("activePage", page);
        return modelAndView;
    }

    @RequestMapping(value = "/getip",method = RequestMethod.GET)
    @ResponseBody
    public String getIp(HttpServletRequest httpServletRequest) {
        String addr = httpServletRequest.getRemoteAddr();
        return addr;
    }
}
