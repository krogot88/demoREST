package ru.krogot88.demorest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.krogot88.demorest.model.Person;
import ru.krogot88.demorest.model.Word;
import ru.krogot88.demorest.service.ServicePerson;
import ru.krogot88.demorest.service.ServiceWord;
import ru.krogot88.demorest.util.Utils;
import ru.krogot88.demorest.validator.PersonValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private ServiceWord serviceWord;

    @Autowired
    private ServicePerson servicePerson;

    @Autowired
    private PersonValidator personValidator;

    @GetMapping(value = {"/","/index"})
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

    @GetMapping(value = "/login")
    public String getLoginPage(Model model) {
        return "login";
    }

    @GetMapping(value = "/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("personForm",new Person());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String postRegistrationPage(@ModelAttribute("personForm") Person person, BindingResult bindingResult) {
        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors()) {
            return "registration";
        }
        System.out.println(person);
        servicePerson.savePerson(person);
        return "redirect:login";
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
