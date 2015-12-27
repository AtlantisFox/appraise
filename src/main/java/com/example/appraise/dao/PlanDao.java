package com.example.appraise.dao;

import com.example.appraise.model.ArPlan;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlanDao extends BaseDao<ArPlan> {
    public boolean isUsed(int planId) {
        ArPlan plan = findById(planId);
        if (plan != null) {
            boolean result = plan.getStatus() != 0;
            getSession().evict(plan);
            return result;
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public List<ArPlan> findExecutedPlans() {
        Query query = getSession().createQuery("from ArPlan where status!=0");
        return query.list();
    }
}
