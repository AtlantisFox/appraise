package com.example.appraise.service;

import com.example.appraise.model.ArUserSecure;

import javax.servlet.http.HttpSession;

public interface SessionService {
    SessionChecker get(HttpSession session);

    void set(HttpSession session, ArUserSecure user);

    void logout(HttpSession session);
}
