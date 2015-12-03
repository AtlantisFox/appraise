package com.example.appraise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class IndexesController {
    @RequestMapping("/indexes")
    public ModelAndView Indexes(HttpSession session) {
        SessionChecker sessionChecker = new SessionChecker(session);
        if (!sessionChecker.hasAuthorized()) {
            return new ModelAndView(LoginController.REDIRECT_TO_LOGIN);
        }
        if (!sessionChecker.hasAppraisalAdmin()) {
            return new ModelAndView(IndexController.REDIRECT_TO_INDEX);
        }
        return new ModelAndView("indexes");
    }
}
