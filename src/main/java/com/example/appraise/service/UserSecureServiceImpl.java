package com.example.appraise.service;

import com.example.appraise.dao.UserSecureDao;
import com.example.appraise.model.ArUserSecure;
import com.example.appraise.model.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserSecureServiceImpl implements UserSecureService {
    @Autowired
    private UserSecureDao userSecureDao;

    @Override
    public ArUserSecure auth(String username, String password) {
        if (password == null)
            return null;
        ArUserSecure user = userSecureDao.findById(username);
        if (user == null)
            return null;
        if (!password.toLowerCase().equals(user.getPassword().toLowerCase()))
            return null;
        return user;
    }

    @Override
    public void save(ArUserSecure user) throws RestApiException {
        try {
            userSecureDao.save(user);
        } catch (DataIntegrityViolationException e) {
            throw RestApiException.onInvalidParam("duplicated username");
        }
    }

    @Override
    public ArUserSecure updateAdmin(ArUserSecure newUser, String currentUser) throws RestApiException {
        ArUserSecure oldUser = userSecureDao.findById(newUser.getUsername());
        if (oldUser == null)
            throw RestApiException.onInvalidParam("username does not exist");
        if (oldUser.getUsername().equals(currentUser))
            if (oldUser.getIsAccountAdmin() != 0 && newUser.getIsAccountAdmin() == 0)
                throw RestApiException.onInvalidParam("cannot self-revoke");

        if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
            oldUser.setPassword(newUser.getPassword());
        }
        oldUser.setRemark(newUser.getRemark());
        oldUser.setIsAppraisalAdmin(newUser.getIsAppraisalAdmin());
        oldUser.setIsAccountAdmin(newUser.getIsAccountAdmin());
        userSecureDao.save(oldUser);
        return oldUser;
    }

    @Override
    public ArUserSecure update(ArUserSecure newUser, String currentUser) throws RestApiException {
        if (!currentUser.equals(newUser.getUsername()))
            throw RestApiException.onUnprivileged();
        ArUserSecure oldUser = userSecureDao.findById(currentUser);
        if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
            oldUser.setPassword(newUser.getPassword());
        }
        oldUser.setRemark(newUser.getRemark());
        userSecureDao.save(oldUser);
        return oldUser;
    }

    @Override
    public ArUserSecure delete(String username) throws RestApiException {
        ArUserSecure oldUser = userSecureDao.findById(username);
        if (oldUser == null)
            throw RestApiException.onInvalidParam("username does not exist");
        userSecureDao.delete(oldUser);
        return oldUser;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArUserSecure> findAll() {
        List<ArUserSecure> list = userSecureDao.findAll();
        for (ArUserSecure user : list) {
            user.setPassword("");
        }
        return list;
    }
}
