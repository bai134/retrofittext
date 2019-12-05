package com.qcsoft.netlibrary.factory;

import com.qcsoft.baselibrary.utils.TempSettingConfig;
import com.qcsoft.netlibrary.converter.StringConverterFactory;
import com.qcsoft.netlibrary.handler.ProxyHandler;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RemoteApiFactoryUtils {
    private static Retrofit retrofit;
    private static Map<String, Subscription> map;


    private RemoteApiFactoryUtils() {
        map = new HashMap<>();

        //添加超时、拦截器、
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).
                readTimeout(60, TimeUnit.SECONDS).
                writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    //为每一个请求添加token
                    Request request = chain.request().newBuilder().addHeader("token", TempSettingConfig.SP_getToken()).build();
                    return chain.proceed(request);
                })
                .build();

        retrofit = new Retrofit.Builder().baseUrl("http://127.0.0.1:8080").client(client).
                //                        addConverterFactory(GsonConverterFactory.create()).
                        addConverterFactory(StringConverterFactory.create()).//自定义ConverterFactory
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                        build();

    }

    private static class StaticSingletonHolder {
        private static final RemoteApiFactoryUtils instance = new RemoteApiFactoryUtils();
    }

    public static synchronized RemoteApiFactoryUtils getInstance() {
        return StaticSingletonHolder.instance;
    }

    public <T> T createRemoteApi(Class<T> clazz) {
        T api;
        try {
            api = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, new ProxyHandler(retrofit.create(clazz)));
        } catch (Exception e) {
            e.printStackTrace();
            api = retrofit.create(clazz);
        }
        return api;
    }

    /**
     * 请求订阅
     *
     * @param flowable 接口Flowable
     * @param callback 回调
     * @param <T>
     */
    public <T> void executeMethod(Flowable<T> flowable, final OnCallBack<T> callback) {
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWhen(3, 3000, retrofit))
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (callback != null) {
                            callback.onError(e);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (callback != null) {
                            callback.onComplete();
                        }
                    }

                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(T o) {
                        if (callback != null) {
                            callback.onSucceed(o);
                        }
                    }
                });
    }

    /**
     * 取消还未返回的重复请求订阅
     *
     * @param url      标识
     * @param flowable 接口Flowable
     * @param callback 回调
     * @param <T>
     */
    public <T> void executeMethod(final String url, Flowable<T> flowable, final OnCallBack<T> callback) {
        if (map.get(url) != null) {
            map.get(url).cancel();
            map.remove(url);
        }
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWhen(3, 3000, retrofit))
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (callback != null) {
                            callback.onError(e);
                        }
                        map.remove(url);
                    }

                    @Override
                    public void onComplete() {
                        if (callback != null) {
                            callback.onComplete();
                        }
                        map.remove(url);
                    }

                    @Override
                    public void onSubscribe(Subscription s) {
                        map.put(url, s);
                        s.request(Long.MAX_VALUE);

                    }

                    @Override
                    public void onNext(T o) {
                        if (callback != null) {
                            callback.onSucceed(o);
                        }
                    }
                });
    }

    /**
     * 取消请求
     */
    public void cleanRequest() {
        if (map == null || map.size() == 0)
            return;
        for (String s : map.keySet()) {
            if (map.get(s) != null)
                map.get(s).cancel();
        }

    }

    public interface OnCallBack<T> {
        void onSucceed(T data);

        void onComplete();

        void onError(Throwable e);

    }

}
