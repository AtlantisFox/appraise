package com.example.appraise.api;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 当任何RequestHandler抛出RestApiException时，
 * 由本类负责处理，将错误信息格式化，返回给调用方。
 */
@ControllerAdvice
@RestController
public class RestApiExceptionHandler {
    @ExceptionHandler(RestApiException.class)
    public RestApiExceptionMsg handle(
            RestApiException e,
            HttpServletResponse response) {
        response.setStatus(e.getError());
        return new RestApiExceptionMsg(e);
    }

    /**
     * This class is almost the same as RestApiException,
     * except that it doesn't inherit from Exception.
     * Used to present error code and message to end user,
     * without leaking detailed (and meaningless) stack trace.
     */
    private static class RestApiExceptionMsg {
        private final int error;
        private final String msg;

        private RestApiExceptionMsg(RestApiException e) {
            this.error = e.getError();
            this.msg = e.getMsg();
        }

        public int getError() {
            return error;
        }

        public String getMsg() {
            return msg;
        }
    }
}
