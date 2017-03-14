package kz.ally.okhttp.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 作者：ZhouYou
 * 日期：2017/3/14.
 */
public class RequestHeader {

    private Map<String, String> headers;

    public RequestHeader() {
        headers = new HashMap<>();
    }

    public boolean isEmpty() {
        return headers.isEmpty();
    }

    public void put(String key, String value) {
        headers.put(key, value);
    }

    public String get(String key) {
        return headers.get(key);
    }

    public Set<String> keySet() {
        return headers.keySet();
    }
}
