package com.example.appraise.api;

import com.example.appraise.model.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
public class BatchRestController extends BaseRestApiController {
    @Autowired
    private UserRestController userCtrl;

    @Autowired
    private IndexRestController indexCtrl;

    @Autowired
    private AppraiseRestController apprCtrl;

    @RequestMapping(value = "/api/batch")
    public HashMap request(
            @RequestParam(value = "users", required = false) boolean users,
            @RequestParam(value = "indexes", required = false) boolean indexes,
            @RequestParam(value = "usedIndexes", required = false) boolean usedIndexes,
            @RequestParam(value = "apprPlans", required = false) boolean apprPlans,
            @RequestParam(value = "apprUnfinished", required = false) boolean apprUnfinished,
            @RequestParam(value = "me", required = false) boolean me,
            HttpSession session) throws RestApiException {
        HashMap<String, Object> result = new HashMap<>();
        if (users)
            result.put("users", userCtrl.list(session));
        if (indexes)
            result.put("indexes", indexCtrl.list(session));
        if (usedIndexes)
            result.put("usedIndexes", indexCtrl.listUsed(session));
        if (apprPlans)
            result.put("apprPlans", apprCtrl.getPlans(session));
        if (apprUnfinished)
            result.put("apprUnfinished", apprCtrl.unfinished(session));
        if (me)
            result.put("me", userCtrl.me(session));
        return result;
    }
}
