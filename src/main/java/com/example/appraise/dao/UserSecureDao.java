package com.example.appraise.dao;

import com.example.appraise.model.ArUserSecure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserSecureDao extends BaseDao {
    public ArUserSecure findById(String username) {
        return (ArUserSecure) getSession().get(ArUserSecure.class, username);
    }

    public void persist(ArUserSecure user) {
        getSession().persist(user);
    }

    public void update(ArUserSecure user) {
        getSession().update(user);
    }

    public void delete(ArUserSecure user) {
        getSession().delete(user);
    }

    @SuppressWarnings("unchecked")
    public List<ArUserSecure> findAll() {
        return getSession().createQuery("from ArUserSecure order by username").list();
    }
}
