package com.bai.retrofittextapplication.Utils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteApiFactoryUtils {
    private static Retrofit retrofit;
    private static OkHttpClient client;

    static {
        client = new OkHttpClient();

        retrofit = new Retrofit.Builder().baseUrl("http://127.0.0.1:8080").client(client).
                        addConverterFactory(GsonConverterFactory.create()).
//                        addConverterFactory(StringConverterFactory.create()).//自定义ConverterFactory
                        addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                        build();

    }

    public static <API> API createRemoteApi(Class<API> clazz) {
        return retrofit.create(clazz);
    }

    public static <T> void executeMethod(Flowable<T> flowable, final OnCallBack<T> callback) {
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

    public interface OnCallBack<T> {
        void onSucceed(T data);

        void onComplete();

        void onError(Throwable e);

    }

}
