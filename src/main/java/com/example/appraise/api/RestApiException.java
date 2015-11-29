package com.example.appraise.api;

/**
 * 存储调用API的错误信息
 */
public class RestApiException extends Exception {
    private final int error;
    private final String msg;

    public RestApiException(int error, String msg) {
        this.error = error;
        this.msg = msg;
    }

    public static RestApiException onInvalidParam() {
        return new RestApiException(400, "invalid param");
    }

    public static RestApiException onInvalidParam(String desc) {
        return new RestApiException(400, "invalid param: " + desc);
    }

    public static RestApiException onUnauthorized() {
        return new RestApiException(401, "unauthorized");
    }

    public static RestApiException onUnprivileged() {
        return new RestApiException(403, "unprivileged");
    }

    public int getError() {
        return error;
    }

    public String getMsg() {
        return msg;
    }
}