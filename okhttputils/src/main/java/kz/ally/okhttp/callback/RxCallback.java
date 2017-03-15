package kz.ally.okhttp.callback;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import kz.ally.okhttp.gson.GsonMapper;
import okhttp3.Response;

/**
 * 作者：ZhouYou
 * 日期：2017/3/15.
 */
public abstract class RxCallback<T>  {

    public Observable<T> parseResponse(Response resp) throws IOException {
        String result = resp.body().string();
        Class<T> clazz = transform();
        final T t = GsonMapper.getInstance().getGson().fromJson(result, clazz);
        Observable<T> observable = Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                e.onNext(t);
            }
        });
        observable.observeOn(Schedulers.io());
        return observable;
    }

    private Class<T> transform() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] type = parameterizedType.getActualTypeArguments();
        return (Class<T>) type[0];
    }
}
