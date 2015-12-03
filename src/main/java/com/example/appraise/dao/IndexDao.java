package com.example.appraise.dao;

import com.example.appraise.model.ArIndex;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IndexDao extends BaseDao<ArIndex> {
    @SuppressWarnings("unchecked")
    public List<Integer> findUsedIndexes() {
        return getSession().createQuery("select distinct planId from ArPlanIndex").list();
    }

    public boolean isUsed(int id) {
        Query query = getSession().createQuery("from ArPlanIndex where planId=:id");
        query.setInteger("id", id);
        query.setMaxResults(1);
        return query.list().size() != 0;
    }

    public boolean exist(ArIndex entity) {
        ArIndex tmp = findById(entity.getId());
        if (tmp != null) {
            getSession().evict(tmp);
            return true;
        } else {
            return false;
        }
    }
}
