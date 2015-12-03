package com.example.appraise.service;

import com.example.appraise.dao.UserSecureDao;
import com.example.appraise.model.ArUserSecure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
public class SessionService {
    private static final String SESSION_ATTR = "$LOGGED_IN_USERNAME$";

    @Autowired
    private UserSecureDao userSecureDao;

    @Transactional(readOnly = true)
    public SessionChecker get(HttpSession session) {
        String username = (String) session.getAttribute(SESSION_ATTR);
        if (username == null) {
            return new SessionChecker(null);
        } else {
            return new SessionChecker(userSecureDao.findById(username));
        }
    }

    public void set(HttpSession session, ArUserSecure user) {
        session.setAttribute(SESSION_ATTR, user.getUsername());
    }

    public void logout(HttpSession session) {
        session.setAttribute(SESSION_ATTR, null);
    }
}
