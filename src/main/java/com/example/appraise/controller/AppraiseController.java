package com.example.appraise.controller;

import com.example.appraise.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AppraiseController {
    @Autowired
    private SessionService sessionService;

    private String requireAuth(HttpSession session, String modal) {
        if (!sessionService.get(session).hasAuthorized())
            return LoginController.REDIRECT_TO_LOGIN;
        return modal;
    }

    @RequestMapping("/appr_plans")
    public String apprPlans(HttpSession session) {
        return requireAuth(session, "appr_plans");
    }

    @RequestMapping("/appr_plan")
    public String apprPlan(HttpSession session) {
        return requireAuth(session, "appr_plan");
    }

    @RequestMapping("/appr_result")
    public String apprResult(HttpSession session) {
        return requireAuth(session, "appr_result");
    }
}
