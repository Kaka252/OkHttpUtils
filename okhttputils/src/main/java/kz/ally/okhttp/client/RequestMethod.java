package kz.ally.okhttp.client;

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

    public String getValue() {
        return value;
    }
}
