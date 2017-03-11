package kz.ally.okhttp.interfaces;

import java.util.Map;

import kz.ally.okhttp.config.Params;
import kz.ally.okhttp.method.BaseRequestBuilder;

/**
 * 作者：ZhouYou
 * 日期：2017/2/23.
 */
public interface IParams {

    BaseRequestBuilder addParam(String key, String value);
    BaseRequestBuilder addParams(Map<String, String> p);
    BaseRequestBuilder addParams(Params params);
}
