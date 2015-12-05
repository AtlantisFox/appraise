package com.example.appraise.api;

import com.example.appraise.model.ArPlan;
import com.example.appraise.model.ArPlanPackage;
import com.example.appraise.service.PlanService;
import com.example.appraise.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/api/plan")
public class PlanRestController extends BaseRestApiController {
    @Autowired
    private PlanService planService;

    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "delete")
    public boolean delete(ArPlan plan, HttpSession session) throws RestApiException {
        sessionService.get(session).requireAppraisalAdmin();
        planService.delete(plan.getId());
        return true;
    }

    @RequestMapping(value = "list")
    public List<ArPlan> list(HttpSession session) throws RestApiException {
        sessionService.get(session).requireAppraisalAdmin();
        return planService.list();
    }

    @RequestMapping(value = "get")
    public ArPlanPackage get(int id, HttpSession session) throws RestApiException {
        sessionService.get(session).requireAppraisalAdmin();
        return planService.findById(id);
    }

    @RequestMapping(value = "save")
    public ArPlanPackage save(@RequestBody ArPlanPackage pack, HttpSession session) throws RestApiException {
        sessionService.get(session).requireAppraisalAdmin();
        return planService.saveOrUpdate(pack);
    }
}
