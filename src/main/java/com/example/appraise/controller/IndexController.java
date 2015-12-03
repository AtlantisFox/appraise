package com.example.appraise.controller;

import com.example.appraise.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    public static final String REDIRECT_TO_INDEX = "redirect:index";

    @Autowired
    private SessionService sessionService;

    @RequestMapping("/index")
    public ModelAndView Index(HttpSession session) {
        if (!sessionService.get(session).hasAuthorized()) {
            return new ModelAndView(LoginController.REDIRECT_TO_LOGIN);
        }
        return new ModelAndView("index");
    }
}
