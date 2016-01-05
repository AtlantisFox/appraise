package com.example.appraise.service;

import com.example.appraise.dao.UserDao;
import com.example.appraise.model.ArUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public boolean exist(String username) {
        return userDao.exist(username);
    }

    @Override
    public List<ArUser> findAll() {
        return userDao.findAll();
    }
}
