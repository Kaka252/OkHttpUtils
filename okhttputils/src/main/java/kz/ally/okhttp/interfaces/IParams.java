package kz.ally.okhttp.interfaces;

import kz.ally.okhttp.config.Params;
import kz.ally.okhttp.method.BaseRequestBuilder;

/**
 * 作者：ZhouYou
 * 日期：2017/2/23.
 */
public interface IParams {

    BaseRequestBuilder addParam(String key, Object value);
    BaseRequestBuilder addParams(Params params);
}
