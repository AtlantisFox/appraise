package com.example.appraise.api;

import com.example.appraise.model.*;
import com.example.appraise.service.PlanExecutionService;
import com.example.appraise.service.SessionChecker;
import com.example.appraise.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 执行考评方案的接口
 */
@RestController
@RequestMapping(value = "/api/appraise")
public class AppraiseRestController extends BaseRestApiController {
    @Autowired
    private PlanExecutionService planExecutionService;

    @Autowired
    private SessionService sessionService;

    /**
     * 考评管理员执行给定考评方案
     *
     * @param planId  方案编号
     * @param session Session
     * @return 执行结果（无意义，任何失败都会丢异常）
     * @throws RestApiException
     */
    @RequestMapping(value = "execute")
    public boolean execute(int planId, HttpSession session) throws RestApiException {
        sessionService.get(session).requireAppraisalAdmin();
        planExecutionService.execute(planId);
        return true;
    }

    /**
     * 考评管理员重置给定正在考评的方案
     *
     * @param planId  方案编号
     * @param session Session
     * @return 执行结果（无意义，任何失败都会丢异常）
     * @throws RestApiException
     */
    @RequestMapping(value = "reset")
    public boolean reset(int planId, HttpSession session) throws RestApiException {
        sessionService.get(session).requireAppraisalAdmin();
        planExecutionService.reset(planId);
        return true;
    }

    /**
     * 考评人员针对给定考评方案进行评分
     *
     * @param planId  方案编号
     * @param result  评分结果（通过POST方法，在body中以JSON格式提交）
     * @param session Session
     * @return 当前考评人员是否已完成当前方案的所有评分
     * @throws RestApiException
     */
    @RequestMapping(value = "appraise")
    public boolean appraise(int planId, @RequestBody List<ArResult> result, HttpSession session) throws RestApiException {
        SessionChecker checker = sessionService.get(session);
        checker.requireAuthorized();
        return planExecutionService.appraise(checker.getUsername(), planId, result);
    }

    /**
     * 获得当前考评人员的所有未完成考评方案
     *
     * @param session Session
     * @return 未完成方案的编号的列表
     * @throws RestApiException
     */
    @RequestMapping(value = "unfinished")
    public List<Integer> unfinished(HttpSession session) throws RestApiException {
        SessionChecker checker = sessionService.get(session);
        checker.requireAuthorized();
        return planExecutionService.findUnfinished(checker.getUsername());
    }

    /**
     * 获得正在考评中（以及考评完）的方案列表
     *
     * @param session Session
     * @return 方案列表
     * @throws RestApiException
     */
    @RequestMapping(value = "plans")
    public List<ArPlan> getPlans(HttpSession session) throws RestApiException {
        sessionService.get(session).requireAuthorized();
        return planExecutionService.findExecutedPlans();
    }

    /**
     * 获得给定方案的指标编号和权重
     *
     * @param planId  方案编号
     * @param session Session
     * @return 编号+权重
     * @throws RestApiException
     */
    @RequestMapping(value = "plan")
    public List<ArPlanIndex> getPlan(int planId, HttpSession session) throws RestApiException {
        sessionService.get(session).requireAuthorized();
        return planExecutionService.findPlanIndexes(planId);
    }

    /**
     * 考评人员获得给定方案，自己已经提交的所有评分数据
     *
     * @param planId  方案编号
     * @param session Session
     * @return 评分数据
     * @throws RestApiException
     */
    @RequestMapping(value = "appraiser_results")
    public List<ArResult> getAppraiserResult(int planId, HttpSession session) throws RestApiException {
        SessionChecker checker = sessionService.get(session);
        checker.requireAuthorized();
        return planExecutionService.getAppraiserResult(planId, checker.getUsername());
    }

    /**
     * 获得给定方案的所有用户的得分统计数据
     *
     * @param planId  方案编号
     * @param session Session
     * @return 得分统计数据
     * @throws RestApiException
     */
    @RequestMapping(value = "summary")
    public List<ArSummary> summary(int planId, HttpSession session) throws RestApiException {
        SessionChecker checker = sessionService.get(session);
        checker.requireAuthorized();
        return planExecutionService.getSummary(planId, checker.hasAppraisalAdmin());
    }

    /**
     * 获得给定方案、所有用户、每一项指标的得分数据
     *
     * @param planId  方案编号
     * @param session Session
     * @return 得分明细数据
     * @throws RestApiException
     */
    @RequestMapping(value = "result")
    public List<ArResult> result(int planId, HttpSession session) throws RestApiException {
        SessionChecker checker = sessionService.get(session);
        checker.requireAuthorized();
        return planExecutionService.getResult(planId, checker.hasAppraisalAdmin());
    }
}
