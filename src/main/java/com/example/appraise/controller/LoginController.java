package com.example.appraise.controller;

import com.example.appraise.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    // 当必须用户登录的Controller，检查到用户没有登陆时，使用这个作为Model，返回登陆页。
    public static final String REDIRECT_TO_LOGIN = "redirect:login";

    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView Login(HttpSession session) {
        if (!sessionService.get(session).hasAuthorized()) {
            return new ModelAndView("login");
        } else {
            return new ModelAndView("redirect:index");
        }
    }
}
