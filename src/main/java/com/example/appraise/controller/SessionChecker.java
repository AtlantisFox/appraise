package com.example.appraise.controller;

import com.example.appraise.api.RestApiException;
import com.example.appraise.model.ArUserSecure;

import javax.servlet.http.HttpSession;

/**
 * SessionChecker负责管理当前登录用户的Session信息。
 * 由于存放的信息中含有用户的密码（考虑删除），因此不希望jsp/java直接访问。
 */
public class SessionChecker {
    private static final String SESSION_ATTR = "$SecureUser$";
    private final HttpSession session;

    public SessionChecker(HttpSession session) {
        this.session = session;
    }

    public String getUsername() {
        return this.getUser().getUsername();
    }

    private ArUserSecure getUser() {
        return (ArUserSecure) session.getAttribute(SESSION_ATTR);
    }

    public void setUser(ArUserSecure user) {
        session.setAttribute(SESSION_ATTR, user);
    }

    public boolean hasAuthorized() {
        return this.getUser() != null;
    }

    public boolean hasAccountAdmin() {
        return hasAuthorized() && this.getUser().getIsAccountAdmin() != 0;
    }

    public boolean hasAppraisalAdmin() {
        return hasAuthorized() && this.getUser().getIsAppraisalAdmin() != 0;
    }

    public SessionChecker requireAuthorized() throws RestApiException {
        if (!hasAuthorized())
            throw RestApiException.onUnauthorized();
        return this;
    }

    public SessionChecker requireAccountAdmin() throws RestApiException {
        if (!hasAccountAdmin())
            throw RestApiException.onUnprivileged();
        return this;
    }

    public SessionChecker requireAppraisalAdmin() throws RestApiException {
        if (!hasAppraisalAdmin())
            throw RestApiException.onUnprivileged();
        return this;
    }
}
