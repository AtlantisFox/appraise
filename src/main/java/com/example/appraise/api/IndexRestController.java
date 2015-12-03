package com.example.appraise.api;

import com.example.appraise.model.ArIndex;
import com.example.appraise.service.IndexService;
import com.example.appraise.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/api/index")
public class IndexRestController extends BaseRestApiController {
    @Autowired
    private IndexService indexService;

    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "create")
    public ArIndex create(ArIndex index, HttpSession session) throws RestApiException {
        sessionService.get(session).requireAppraisalAdmin();
        return indexService.save(index);
    }

    @RequestMapping(value = "update")
    public ArIndex update(ArIndex index, HttpSession session) throws RestApiException {
        sessionService.get(session).requireAppraisalAdmin();
        return indexService.update(index);
    }

    @RequestMapping(value = "delete")
    public ArIndex delete(ArIndex index, HttpSession session) throws RestApiException {
        sessionService.get(session).requireAppraisalAdmin();
        return indexService.delete(index.getId());
    }

    @RequestMapping(value = "list")
    public List<ArIndex> list(HttpSession session) throws RestApiException {
        sessionService.get(session).requireAppraisalAdmin();
        return indexService.findAll();
    }

    @RequestMapping(value = "listUsed")
    public List<Integer> listUsed(HttpSession session) throws RestApiException {
        sessionService.get(session).requireAppraisalAdmin();
        return indexService.findUsedIndexes();
    }
}
