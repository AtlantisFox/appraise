package com.example.appraise.service;

import com.example.appraise.model.ArUserSecure;
import com.example.appraise.model.RestApiException;

import java.util.List;

public interface UserSecureService {
    ArUserSecure auth(String username, String password);

    void save(ArUserSecure user) throws RestApiException;

    ArUserSecure updateAdmin(ArUserSecure newUser, String currentUser) throws RestApiException;

    ArUserSecure update(ArUserSecure newUser, String currentUser) throws RestApiException;

    ArUserSecure delete(String username) throws RestApiException;

    List<ArUserSecure> findAll();
}
