package com.example.appraise.dao;

import com.example.appraise.model.ArUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao extends BaseDao {
    public ArUser findById(String username) {
        return (ArUser) getSession().get(ArUser.class, username);
    }

    @SuppressWarnings("unchecked")
    public List<ArUser> findAll() {
        return getSession().createQuery("from ArUser order by username").list();
    }
}
