package com.example.appraise.service;

import com.example.appraise.api.RestApiException;
import com.example.appraise.model.ArUserSecure;

public class SessionChecker {
    private final ArUserSecure user;

    public SessionChecker(ArUserSecure user) {
        this.user = user;
    }

    public String getUsername() {
        return user != null ? user.getUsername() : null;
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
