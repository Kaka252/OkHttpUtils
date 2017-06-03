package kz.ally.okhttp.client;

/**
 * Author: ZhouYou
 * Date: 2017/5/22.
 */
public abstract class AbsResponse {

    public long code;

    public String message;

    public boolean isSuccess() {
        return code == 0;
    }

    public boolean isTokenExpired() {
        return code == 7;
    }
}
