package com.example.appraise.service;

import com.example.appraise.dao.IndexDao;
import com.example.appraise.dao.PlanDao;
import com.example.appraise.dao.PlanIndexDao;
import com.example.appraise.model.ArPlan;
import com.example.appraise.model.ArPlanIndex;
import com.example.appraise.model.ArPlanPackage;
import com.example.appraise.model.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {
    @Autowired
    private PlanDao planDao;

    @Autowired
    private PlanIndexDao planIndexDao;

    @Autowired
    private IndexDao indexDao;

    private void validatePackage(ArPlanPackage pack) throws RestApiException {
        if (pack.getMeta() == null)
            throw RestApiException.onInvalidParam("'meta' is required");
        if (pack.getIndexes() == null)
            throw RestApiException.onInvalidParam("'indexes' is required");
        validateIndexes(pack.getIndexes());
        pack.getMeta().setStatus(0);
    }

    private void validateIndexes(List<ArPlanIndex> indexes) throws RestApiException {
        Set<Integer> set = new HashSet<>();
        for (ArPlanIndex index : indexes) {
            if (index == null)
                throw RestApiException.onInvalidParam("'index' entity cannot be null/invalid index");
            int indexId = index.getIndexId();
            if (set.contains(indexId))
                throw RestApiException.onInvalidParam("duplicated index, id=" + indexId);
            if (!indexDao.exist(indexId))
                throw RestApiException.onInvalidParam("index does not exist, id=" + indexId);
            if (index.getWeight() <= 0)
                throw RestApiException.onInvalidParam("index weight out of range, id=" + indexId);
            set.add(indexId);
        }
    }

    @Override
    public ArPlanPackage saveOrUpdate(ArPlanPackage pack) throws RestApiException {
        validatePackage(pack);
        ArPlan plan = pack.getMeta();
        if (planDao.isUsed(plan.getId()))
            throw RestApiException.onInvalidParam("plan is being used");
        planDao.saveOrUpdate(plan);
        planIndexDao.deletePlan(plan.getId());
        planIndexDao.saveBuck(pack.getIndexes(), plan.getId());
        return pack;
    }

    @Override
    public void delete(int planId) throws RestApiException {
        ArPlan plan = planDao.findById(planId);
        if (plan == null) {
            throw RestApiException.onInvalidParam("plan does not exist");
        }
        if (planDao.isUsed(plan.getId()))
            throw RestApiException.onInvalidParam("plan is being used");
        planDao.delete(plan);
        planIndexDao.deletePlan(planId);
    }

    @Override
    public List<ArPlan> list() {
        return planDao.findAll();
    }

    @Override
    public ArPlanPackage findById(int planId) {
        ArPlanPackage pack = new ArPlanPackage();
        pack.setMeta(planDao.findById(planId));
        pack.setIndexes(planIndexDao.findByPlan(planId));
        return pack;
    }
}
