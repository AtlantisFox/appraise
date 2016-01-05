package com.example.appraise.service;

import com.example.appraise.model.ArUser;

import java.util.List;

public interface UserService {
    boolean exist(String username);

    List<ArUser> findAll();
}
