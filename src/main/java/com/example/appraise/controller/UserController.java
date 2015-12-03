package com.example.appraise.controller;

import com.example.appraise.service.SessionChecker;
import com.example.appraise.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private SessionService sessionService;

    @RequestMapping("/users")
    public ModelAndView Users(HttpSession session) {
        SessionChecker sessionChecker = sessionService.get(session);
        if (!sessionChecker.hasAuthorized()) {
            return new ModelAndView(LoginController.REDIRECT_TO_LOGIN);
        }
        if (!sessionChecker.hasAccountAdmin()) {
            return new ModelAndView(IndexController.REDIRECT_TO_INDEX);
        }
        return new ModelAndView("users");
    }
}
