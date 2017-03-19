package kz.ally.okhttp.request.rx;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.Response;

/**
 * Created by zhouyou on 17/3/19.
 */

public class RxResultFunc<T> implements Function<Response, T> {

    @Override
    public T apply(@NonNull Response response) throws Exception {
        if (response.isSuccessful()) {

        }
        return null;
    }
}
