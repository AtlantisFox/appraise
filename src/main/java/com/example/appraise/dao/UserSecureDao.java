package com.example.appraise.dao;

import com.example.appraise.model.ArUserSecure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserSecureDao extends BaseDao<ArUserSecure> {
    @SuppressWarnings("unchecked")
    public List<ArUserSecure> findAll() {
        return getSession().createQuery("from ArUserSecure order by username").list();
    }
}
