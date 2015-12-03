package com.example.appraise.dao;

import com.example.appraise.model.ArUser;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao<ArUser> {
    public boolean exist(String username) {
        ArUser tmp = findById(username);
        if (tmp != null) {
            getSession().evict(tmp);
            return true;
        } else {
            return false;
        }
    }
}
