package com.example.appraise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    public static final String REDIRECT_TO_INDEX = "redirect:index";

    @RequestMapping("/index")
    public ModelAndView Index(HttpSession session) {
        if (!new SessionChecker(session).hasAuthorized()) {
            return new ModelAndView(LoginController.REDIRECT_TO_LOGIN);
        }
        return new ModelAndView("index");
    }
}
