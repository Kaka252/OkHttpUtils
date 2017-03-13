package kz.ally.okhttp.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 作者：ZhouYou
 * 日期：2017/2/24.
 * 参数
 */
public class Params {

    private static final String TAG = "URLParams";

    private Map<String, Object> params;

    private Map<String, File> fileParams;

    public Params() {
        params = new HashMap<>();
        fileParams = new HashMap<>();
    }

    public void put(String key, Object value) {
        params.put(key, value);
    }

    public void put(String key, File f) {
        fileParams.put(key, f);
    }

    public boolean isEmpty() {
        return params == null || params.isEmpty();
    }

    public Set<String> keySet() {
        return params.keySet();
    }

    public Object get(String key) {
        return params.get(key);
    }

    public Set<String> fileSet() {
        return fileParams.keySet();
    }

    public File getFile(String key) {
        return fileParams.get(key);
    }

    public boolean hasFile() {
        return fileParams == null || fileParams.isEmpty();
    }

}
