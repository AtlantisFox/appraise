package com.example.appraise.api;

import com.example.appraise.model.RestApiException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 所有 rest api controller 的基类。
 * 负责处理异常，并以 restful 的格式返回。
 */
@RestController
public abstract class BaseRestApiController {
    @ExceptionHandler(RestApiException.class)
    public Object onRestError(RestApiException e, HttpServletResponse resp) {
        resp.setStatus(e.getError());
        return renderErrorMsg(e.getError(), e.getMsg());
    }

    /*
    // 处理所有异常，调试时可以注释掉，以返回可读性较高的错误提示页面
    @ExceptionHandler(Exception.class)
    public Object onUnknownError(Exception e, HttpServletResponse resp) {
        resp.setStatus(500);
        return renderErrorMsg(500, "unknown error");
    }
    */

    private Object renderErrorMsg(int error, String msg) {
        HashMap<String, Object> resp = new HashMap<>();
        resp.put("error", error);
        resp.put("msg", msg);
        return resp;
    }
}
