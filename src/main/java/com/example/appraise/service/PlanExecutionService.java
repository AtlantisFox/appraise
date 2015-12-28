package com.example.appraise.service;

import com.example.appraise.model.RestApiException;
import com.example.appraise.dao.*;
import com.example.appraise.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class PlanExecutionService {
    @Autowired
    private PlanIndexDao planIndexDao;

    @Autowired
    private IndexDao indexDao;

    @Autowired
    private PlanDao planDao;

    @Autowired
    private ResultDao resultDao;

    @Autowired
    private SummaryDao summaryDao;

    @Autowired
    private UserDao userDao;

    public void execute(int planId) throws RestApiException {
        ArPlan plan = planDao.findById(planId);
        if (plan == null)
            throw RestApiException.onInvalidParam("plan id does not exist");
        if (plan.getStatus() != 0)
            throw RestApiException.onInvalidParam("plan is not available");
        plan.setStatus(1);
        planDao.update(plan);
        List<ArUser> userList = userDao.findAll();
        List<ArPlanIndex> indexList = planIndexDao.findByPlan(planId);
        if (userList == null || indexList == null)
            throw RestApiException.onSystemErr("cannot fetch base data");

        resultDao.deleteByPlan(planId);
        summaryDao.deleteByPlan(planId);

        Set<String> appraisers = new HashSet<>();
        for (ArPlanIndex planIndex : indexList) {
            ArIndex index = indexDao.findById(planIndex.getIndexId());
            if (index == null)
                throw RestApiException.onInvalidParam("plan definition error, index not found, id=" + planIndex.getIndexId());
            appraisers.add(index.getAppraiser());
        }
        for (ArUser user : userList) {
            ArSummary summary = new ArSummary();
            summary.setPlan(planId);
            summary.setUser(user.getUsername());
            summary.setFinished(appraisers.contains(user.getUsername()) ? 0 : 1);
            summaryDao.save(summary);
        }
    }

    public void reset(int planId) throws RestApiException {
        ArPlan plan = planDao.findById(planId);
        if (plan == null)
            throw RestApiException.onInvalidParam("plan not fonnd, id=" + planId);
        plan.setStatus(0);
        planDao.update(plan);
        summaryDao.deleteByPlan(planId);
        resultDao.deleteByPlan(planId);
    }

    public boolean appraise(String appraiser, int planId, List<ArResult> resultList) throws RestApiException {
        ArPlan plan = planDao.findById(planId);
        if (plan == null)
            throw RestApiException.onInvalidParam("invalid plan id");
        if (plan.getStatus() != 1)
            throw RestApiException.onInvalidParam("plan state error");

        // 获取考评方案所关联的所有指标的数据
        Map<Integer, ArPlanIndex> planIndexMap = new HashMap<>();
        Map<Integer, ArIndex> indexMap = new HashMap<>();
        for (ArPlanIndex planIndex : planIndexDao.findByPlan(planId)) {
            planIndexMap.put(planIndex.getIndexId(), planIndex);
            ArIndex index = indexDao.findById(planIndex.getIndexId());
            if (index == null)
                throw RestApiException.onInvalidParam("plan definition error, index not found, id=" + planIndex.getIndexId());
            indexMap.put(planIndex.getIndexId(), index);
        }
        List<ArUser> userList = userDao.findAll();

        Set<String> updatedUsers = new HashSet<>();
        for (ArResult result : resultList) {
            // 验证考评方案参数
            if (result.getPlanId() == 0) {
                result.setPlanId(planId);
            } else if (result.getPlanId() != planId) {
                throw RestApiException.onInvalidParam("inconsistent plan id");
            }
            // 验证指标参数
            ArPlanIndex planIndex = planIndexMap.get(result.getIndexId());
            if (planIndex == null)
                throw RestApiException.onInvalidParam("no such plan/index: plan=" + result.getPlanId() + ", index=" + result.getIndexId());
            ArIndex index = indexMap.get(result.getIndexId());

            // 验证考评者权限
            if (!index.getAppraiser().equals(appraiser))
                throw RestApiException.onInvalidParam("user is not appraiser to index: " + result.getIndexId());

            // 验证被考评者范围
            if (userDao.findById(result.getAppraisee()) == null)
                throw RestApiException.onInvalidParam("appraisee does not exist: " + result.getAppraisee());
            String appraisee = index.getAppraisee();
            if (appraisee != null && !appraisee.isEmpty()) {
                if (!appraisee.equals(result.getAppraisee()))
                    throw RestApiException.onInvalidParam("user is not appraisee: " + result.getAppraisee());
            }

            // 验证分数范围
            if (result.getPoint() < 0 || result.getPoint() > index.getPoint())
                throw RestApiException.onInvalidParam("point out of range");

            resultDao.saveOrUpdate(result);
            updatedUsers.add(result.getAppraisee());
        }

        // 更新考评者的完成状态
        boolean finished = true;
        for (Integer indexId : indexMap.keySet()) {
            ArIndex index = indexMap.get(indexId);
            if (!index.getAppraiser().equals(appraiser))
                continue;
            // 对于当前方案的每一个指标
            if (index.getAppraisee().isEmpty()) {
                // 共性指标，要给所有用户全部考评后才算完成
                for (ArUser user : userList) {
                    ArResultPK resultPK = new ArResultPK();
                    resultPK.setPlanId(planId);
                    resultPK.setIndexId(indexId);
                    resultPK.setAppraisee(user.getUsername());
                    if (resultDao.findById(resultPK) == null) {
                        finished = false;
                        break;
                    }
                }
            } else {
                // 特性指标，只要给特定用户考评就算完成
                ArResultPK resultPK = new ArResultPK();
                resultPK.setPlanId(planId);
                resultPK.setIndexId(indexId);
                resultPK.setAppraisee(index.getAppraisee());
                if (resultDao.findById(resultPK) == null) {
                    finished = false;
                }
            }
            if (!finished) break;
        }
        if (finished) {
            // 更新考评完成状态
            ArSummaryPK summaryPK = new ArSummaryPK();
            summaryPK.setPlan(planId);
            summaryPK.setUser(appraiser);
            ArSummary summary = summaryDao.findById(summaryPK);
            summary.setFinished(1);
            summaryDao.update(summary);
        }

        // 更新被考评者的得分统计
        for (String appraisee : updatedUsers) {
            double total_common = 0;
            double total_unique = 0;
            double score_common = 0;
            double score_unique = 0;

            for (Integer indexId : indexMap.keySet()) {
                ArIndex index = indexMap.get(indexId);
                String target_appraisee = index.getAppraisee();
                if (!target_appraisee.isEmpty() && !target_appraisee.equals(appraisee))
                    continue;

                ArResultPK resultPK = new ArResultPK();
                resultPK.setAppraisee(appraisee);
                resultPK.setPlanId(planId);
                resultPK.setIndexId(indexId);
                ArResult result = resultDao.findById(resultPK);

                if (result != null) {
                    ArPlanIndex planIndex = planIndexMap.get(indexId);
                    double weight = planIndex.getWeight();
                    if (target_appraisee.isEmpty()) {
                        total_common += index.getPoint() * weight;
                        score_common += result.getPoint() * weight;
                    } else {
                        total_unique += index.getPoint() * weight;
                        score_unique += result.getPoint() * weight;
                    }
                }
            }

            score_common = Math.abs(total_common) > 0.01 ? score_common * 100 / total_common : 0;
            score_unique = Math.abs(total_unique) > 0.01 ? score_unique * 100 / total_unique : 0;
            ArSummaryPK summaryPK = new ArSummaryPK();
            summaryPK.setPlan(planId);
            summaryPK.setUser(appraisee);
            ArSummary summary = summaryDao.findById(summaryPK);
            summary.setPointCommon(score_common);
            summary.setPointUnique(score_unique);
            summaryDao.update(summary);
        }

        // 判断所有用户已经完成打分
        if (summaryDao.isPlanFinished(planId)) {
            plan.setStatus(2);
            planDao.update(plan);
        }
        return finished;
    }

    @Transactional(readOnly = true)
    public List<ArResult> getAppraiserResult(int planId, String appraiser) throws RestApiException {
        Set<Integer> indexes = new HashSet<>();
        for (ArPlanIndex planIndex : planIndexDao.findByPlan(planId)) {
            ArIndex index = indexDao.findById(planIndex.getIndexId());
            if (index == null)
                throw RestApiException.onInvalidParam("plan definition error");
            if (index.getAppraiser().equals(appraiser))
                indexes.add(index.getId());
        }
        List<ArResult> results = new ArrayList<>();
        for (ArResult result : resultDao.findByPlan(planId)) {
            if (indexes.contains(result.getIndexId())) {
                results.add(result);
            }
        }
        return results;
    }

    @Transactional(readOnly = true)
    public List<ArPlan> findAll() {
        return planDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Integer> findUnfinished(String appraiser) {
        List<Integer> results = new ArrayList<>();
        for (ArSummary summary : summaryDao.findByUnfinished(appraiser)) {
            results.add(summary.getPlan());
        }
        return results;
    }

    @Transactional(readOnly = true)
    public List<ArPlan> findExecutedPlans() {
        return planDao.findExecutedPlans();
    }

    @Transactional(readOnly = true)
    public List<ArPlanIndex> findPlanIndexes(int planId) throws RestApiException {
        ArPlan plan = planDao.findById(planId);
        if (plan == null)
            throw RestApiException.onInvalidParam("plan does not exist");
        return planIndexDao.findByPlan(planId);
    }

    @Transactional(readOnly = true)
    public List<ArSummary> getSummary(int planId, boolean isAppraisalAdmin) throws RestApiException {
        ArPlan plan = planDao.findById(planId);
        if (plan == null)
            throw RestApiException.onInvalidParam("plan does not exist");
        if (plan.getStatus() != 2)
            if (!isAppraisalAdmin)
                throw RestApiException.onUnprivileged();
        return summaryDao.findByPlan(planId);
    }

    @Transactional(readOnly = true)
    public List<ArResult> getResult(int planId, boolean isAppraisalAdmin) throws RestApiException {
        ArPlan plan = planDao.findById(planId);
        if (plan == null)
            throw RestApiException.onInvalidParam("plan does not exist");
        if (plan.getStatus() != 2)
            if (!isAppraisalAdmin)
                throw RestApiException.onUnprivileged();
        return resultDao.findByPlan(planId);
    }
}
