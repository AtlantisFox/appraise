package com.example.appraise.api;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 负责处理用户登录的RestApi.
 */
public class Login {
    @RequestMapping("/api/login")
    public String DoLogin() {
        return "";
    }
}
