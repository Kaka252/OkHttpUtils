package kz.ally.okhttp.common;

/**
 * Author: ZhouYou
 * Date: 2017/5/22.
 */
public enum RequestMethod {

    GET("GET"),

    POST("POST"),;

    private final String value;

    RequestMethod(String value) {
        this.value = value;
    }
}
