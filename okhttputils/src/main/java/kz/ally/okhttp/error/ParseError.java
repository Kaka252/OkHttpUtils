package kz.ally.okhttp.error;

import okhttp3.Response;

/**
 * Author: ZhouYou
 * Date: 2017/6/3.
 */
public class ParseError extends AbsError {

    public ParseError() {
    }

    public ParseError(Response response) {
        super(response);
    }

    public ParseError(Throwable cause) {
        super(cause);
    }
}
