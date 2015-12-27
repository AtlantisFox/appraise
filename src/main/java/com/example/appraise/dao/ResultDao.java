package com.example.appraise.dao;

import com.example.appraise.model.ArResult;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResultDao extends BaseDao<ArResult> {
    public int deleteByPlan(int planId) {
        Query query = getSession().createQuery("delete ArResult where planId=:id");
        query.setParameter("id", planId);
        return query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public List<ArResult> findByPlan(int planId) {
        Query query = getSession().createQuery("from ArResult where planId=:id");
        query.setParameter("id", planId);
        return query.list();
    }
}
