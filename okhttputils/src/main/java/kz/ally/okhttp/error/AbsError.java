package kz.ally.okhttp.error;

import okhttp3.Response;

/**
 * Author: ZhouYou
 * Date: 2017/5/27.
 */
public class AbsError extends Exception {

    public final Response response;

    public AbsError() {
        response = null;
    }

    public AbsError(Response response) {
        this.response = response;
    }

    public AbsError(String exceptionMessage) {
        super(exceptionMessage);
        response = null;
    }

    public AbsError(String exceptionMessage, Throwable reason) {
        super(exceptionMessage, reason);
        response = null;
    }

    public AbsError(Throwable cause) {
        super(cause);
        response = null;
    }
}
