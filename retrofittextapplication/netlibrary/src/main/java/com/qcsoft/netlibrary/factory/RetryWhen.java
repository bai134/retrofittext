package com.qcsoft.netlibrary.factory;


import com.qcsoft.baselibrary.utils.TempSettingConfig;

import org.reactivestreams.Publisher;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;

/**
 * Cerated by bailing
 * Create date : 2019/10/14 14:30
 * description : 接口调用失败后重连
 */
public class RetryWhen implements Function<Flowable<Throwable>, Publisher<?>> {
    private int count = 0, maxCount;
    private long time;
    private Retrofit mRetrofit;
    private String TAG = "RetryWhen";

    public RetryWhen(int maxCount, long time, Retrofit retrofit) {
        this.maxCount = maxCount;
        this.time = time;
        mRetrofit = retrofit;
    }

    @Override
    public Publisher<?> apply(Flowable<Throwable> throwableFlowable) throws Exception {

        return throwableFlowable.flatMap(throwable -> {
            if (throwable instanceof IOException || throwable instanceof HttpException) {
                if (count < maxCount) {
                    count++;
                    return Flowable.just(1).delay(time, TimeUnit.MILLISECONDS);
                } else {
                    return Flowable.error(new Throwable("重试次数已超过设置次数 = " + maxCount + "，即 不再重试"));
                }
            } else if (throwable instanceof TokenException) {
                return refreshToken();
            } else {
                return Flowable.error(new Throwable("发生了非网络异常（非I/O异常）"));
            }
        });

    }

    /**
     * 更新token
     * @return
     */
    private Flowable<?> refreshToken() {
        synchronized (RetryWhen.class) {
            return mRetrofit.create(TokenAPI.class).getToken()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .doOnNext(tokenBean -> {
                        if (tokenBean != null && tokenBean.getData() != null && tokenBean.getData().getToken() != null) {
                            TempSettingConfig.SP_saveToken(tokenBean.getData().getToken());
                        }
                    });
        }
    }
}
