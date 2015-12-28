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

@RestController
@RequestMapping(value = "/api/appraise")
public class AppraiseRestController extends BaseRestApiController {
    @Autowired
    private PlanExecutionService planExecutionService;

    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "execute")
    public boolean execute(int planId, HttpSession session) throws RestApiException {
        sessionService.get(session).requireAppraisalAdmin();
        planExecutionService.execute(planId);
        return true;
    }

    @RequestMapping(value = "reset")
    public boolean reset(int planId, HttpSession session) throws RestApiException {
        sessionService.get(session).requireAppraisalAdmin();
        planExecutionService.reset(planId);
        return true;
    }

    @RequestMapping(value = "appraise")
    public boolean appraise(int planId, @RequestBody List<ArResult> result, HttpSession session) throws RestApiException {
        SessionChecker checker = sessionService.get(session);
        checker.requireAuthorized();
        return planExecutionService.appraise(checker.getUsername(), planId, result);
    }

    @RequestMapping(value = "unfinished")
    public List<Integer> unfinished(HttpSession session) throws RestApiException {
        SessionChecker checker = sessionService.get(session);
        checker.requireAuthorized();
        return planExecutionService.findUnfinished(checker.getUsername());
    }

    @RequestMapping(value = "plans")
    public List<ArPlan> getPlans(HttpSession session) throws RestApiException {
        sessionService.get(session).requireAuthorized();
        return planExecutionService.findExecutedPlans();
    }

    @RequestMapping(value = "plan")
    public List<ArPlanIndex> getPlan(int planId, HttpSession session) throws RestApiException {
        sessionService.get(session).requireAuthorized();
        return planExecutionService.findPlanIndexes(planId);
    }

    @RequestMapping(value = "appraiser_results")
    public List<ArResult> getAppraiserResult(int planId, HttpSession session) throws RestApiException {
        SessionChecker checker = sessionService.get(session);
        checker.requireAuthorized();
        return planExecutionService.getAppraiserResult(planId, checker.getUsername());
    }

    @RequestMapping(value = "summary")
    public List<ArSummary> summary(int planId, HttpSession session) throws RestApiException {
        SessionChecker checker = sessionService.get(session);
        checker.requireAuthorized();
        return planExecutionService.getSummary(planId, checker.hasAppraisalAdmin());
    }

    @RequestMapping(value = "result")
    public List<ArResult> result(int planId, HttpSession session) throws  RestApiException {
        SessionChecker checker = sessionService.get(session);
        checker.requireAuthorized();
        return planExecutionService.getResult(planId, checker.hasAppraisalAdmin());
    }
}
