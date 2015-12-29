package com.example.appraise.service;

import com.example.appraise.model.ArUser;
import com.example.appraise.model.ArUserSecure;
import com.example.appraise.model.RestApiException;

/**
 * 根据一个用户对象，提供一系列函数，用于判断用户是否满足一定权限要求。
 */
public class SessionChecker {
    private final ArUserSecure user;

    public SessionChecker(ArUserSecure user) {
        this.user = user;
    }

    public String getUsername() {
        return user != null ? user.getUsername() : null;
    }

    public ArUser getUser() {
        return user != null ? user.toArUser() : null;
    }

    public boolean hasAuthorized() {
        return user != null;
    }

    public boolean hasAccountAdmin() {
        return hasAuthorized() && user.getIsAccountAdmin() != 0;
    }

    public boolean hasAppraisalAdmin() {
        return hasAuthorized() && user.getIsAppraisalAdmin() != 0;
    }

    public SessionChecker requireAuthorized() throws RestApiException {
        if (!hasAuthorized())
            throw RestApiException.onUnauthorized();
        return this;
    }

    public SessionChecker requireAccountAdmin() throws RestApiException {
        requireAuthorized();
        if (!hasAccountAdmin())
            throw RestApiException.onUnprivileged();
        return this;
    }

    public SessionChecker requireAppraisalAdmin() throws RestApiException {
        requireAuthorized();
        if (!hasAppraisalAdmin())
            throw RestApiException.onUnprivileged();
        return this;
    }
}
