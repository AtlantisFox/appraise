package com.example.appraise.dao;

import com.example.appraise.model.ArSummary;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SummaryDao extends BaseDao<ArSummary> {
    public int deleteByPlan(int planId) {
        Query query = getSession().createQuery("delete ArSummary where plan=:id");
        query.setParameter("id", planId);
        return query.executeUpdate();
    }

    public void evict(ArSummary obj) {
        getSession().evict(obj);
    }

    @SuppressWarnings("unchecked")
    public List<ArSummary> findByUnfinished(String appraiser) {
        Query query = getSession().createQuery("from ArSummary where finished=0 and user=:user");
        query.setParameter("user", appraiser);
        return query.list();
    }
}
