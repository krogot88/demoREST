package ru.krogot88.demorest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private WordRepository wordRepository;

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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getListPage(Model model) {
        List<Word> list = wordRepository.findAll();
        model.addAttribute("wordList",list);
        return "list";
    }

    @RequestMapping(value = "/article-list/page/{page}")
    public ModelAndView listArticlesPageByPage(@PathVariable("page") int page) {
        ModelAndView modelAndView = new ModelAndView("article-list-paging");
        PageRequest pageable = PageRequest.of(page - 1, 15);
        Page<Word> articlePage = serviceWord.getPaginatedArticles(pageable);
        int totalPages = articlePage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("activeArticleList", true);
        modelAndView.addObject("articleList", articlePage.getContent());
        return modelAndView;
    }

    @RequestMapping(value = "/getip",method = RequestMethod.GET)
    @ResponseBody
    public String getIp(HttpServletRequest httpServletRequest) {
        String addr = httpServletRequest.getRemoteAddr();
        return addr;
    }
}
