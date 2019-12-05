package com.qcsoft.baselibrary.application;

import android.app.Application;

import io.reactivex.plugins.RxJavaPlugins;

/**
 * Cerated by bailing
 * Create date : 2019/12/5 9:31
 * description :
 */
public class RetrofitTextApplication extends Application {

    private static RetrofitTextApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RxJavaPlugins.setErrorHandler(e -> { });
    }

    public static synchronized RetrofitTextApplication getInstance(){
        return instance;
    }
}
