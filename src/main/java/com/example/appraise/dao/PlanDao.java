package com.example.appraise.dao;

import com.example.appraise.model.ArPlan;
import org.springframework.stereotype.Repository;

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
}
