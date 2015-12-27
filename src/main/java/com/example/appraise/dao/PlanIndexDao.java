package com.example.appraise.dao;

import com.example.appraise.model.ArPlanIndex;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlanIndexDao extends BaseDao<ArPlanIndex> {
    public int deletePlan(int planId) {
        Query query = getSession().createQuery("delete ArPlanIndex where planId=:id");
        query.setParameter("id", planId);
        return query.executeUpdate();
    }

    public void saveBuck(List<ArPlanIndex> indexes, int planId) {
        Session session = getSession();
        for (ArPlanIndex index : indexes) {
            index.setPlanId(planId);
            session.save(index);
        }
    }

    @SuppressWarnings("unchecked")
    public List<ArPlanIndex> findByPlan(int planId) {
        Query query = getSession().createQuery("from ArPlanIndex where planId=:id");
        query.setParameter("id", planId);
        return query.list();
    }
}
