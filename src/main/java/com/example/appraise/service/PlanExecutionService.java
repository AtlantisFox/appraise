package com.example.appraise.service;

import com.example.appraise.model.*;

import java.util.List;

public interface PlanExecutionService {
    void execute(int planId) throws RestApiException;

    void reset(int planId) throws RestApiException;

    /**
     * 保存考评结果
     *
     * @param appraiser  考评人员
     * @param planId     考评方案
     * @param resultList 考评得分，其中的planId字段可以为0，或者必须与上一个参数相同
     * @return 当前考评人员是否完成当前方案所有指标的评分
     * @throws RestApiException
     */
    boolean appraise(String appraiser, int planId, List<ArResult> resultList) throws RestApiException;

    /**
     * 获得指定考评人员在特定方案中的所有评分数据
     *
     * @param planId    方案编号
     * @param appraiser 考评人员
     * @return 评分数据
     * @throws RestApiException
     */
    List<ArResult> getAppraiserResult(int planId, String appraiser) throws RestApiException;

    List<ArPlan> findAll();

    /**
     * 获得指定考评人员所有未完成评分的方案编号
     *
     * @param appraiser 考评人员
     * @return 方案编号列表
     */
    List<Integer> findUnfinished(String appraiser);

    List<ArPlan> findExecutedPlans();

    List<ArPlanIndex> findPlanIndexes(int planId) throws RestApiException;

    List<ArSummary> getSummary(int planId, boolean isAppraisalAdmin) throws RestApiException;

    List<ArResult> getResult(int planId, boolean isAppraisalAdmin) throws RestApiException;
}
