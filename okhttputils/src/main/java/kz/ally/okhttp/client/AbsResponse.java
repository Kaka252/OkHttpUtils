package kz.ally.okhttp.client;

/**
 * Author: ZhouYou
 * Date: 2017/5/22.
 */
public abstract class AbsResponse {

    private static final int SUCCESS = 0;
    private static final int VALIDATION_ERROR = 5;
    private static final int TOKEN_ERROR = 7;
    private static final int SERVER_ERROR_CLASSIFY = 1000;

    public int code;

    public String message;

    /**
     * 业务请求成功
     *
     * @return
     */
    public boolean isSuccess() {
        return code == SUCCESS;
    }

    /**
     * 每天每个手机号码只允许验证5次
     *
     * @return
     */
    public boolean isValidationOverTimes() {
        return code == VALIDATION_ERROR;
    }

    /**
     * T 票过期
     *
     * @return
     */
    public boolean isTokenExpired() {
        return code == TOKEN_ERROR;
    }

    /**
     * 服务器公共类异常
     *
     * @return
     */
    public boolean isServerCommonError() {
        return code > 0 && code < SERVER_ERROR_CLASSIFY;
    }
}
