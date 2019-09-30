package ru.krogot88.demorest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndexPage() {
        return "index";
    }

    @RequestMapping(value = "/play", method = RequestMethod.GET)
    public String getPlayPage() {
        return "play";
    }

}
