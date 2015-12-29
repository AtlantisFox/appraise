package com.example.appraise.controller;

import com.example.appraise.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {
    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView Logout(HttpSession session) {
        sessionService.logout(session);
        return new ModelAndView(LoginController.REDIRECT_TO_LOGIN);
    }
}
