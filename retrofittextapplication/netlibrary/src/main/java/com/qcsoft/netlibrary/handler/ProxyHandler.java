package com.qcsoft.netlibrary.handler;

import com.qcsoft.baselibrary.utils.TempSettingConfig;
import com.qcsoft.netlibrary.factory.RetryWhen;

import org.reactivestreams.Publisher;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;
import retrofit2.http.Header;

/**
 * Cerated by bailing
 * Create date : 2019/11/27 16:39
 * description :
 */
public class ProxyHandler implements InvocationHandler {

    private final static String TAG = "TokenProxy";

    private final static String TOKEN = "token";

    private Object mProxyObject;

    public ProxyHandler(Object proxyObject) {
        mProxyObject = proxyObject;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        return Flowable.just(1).flatMap((Function<Object, Publisher<?>>) o -> {
            try {
                try {
                    updateToken(method, args);
                    return (Publisher<?>) method.invoke(mProxyObject, args);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    /**
     * 更新token
     *
     * @param method 请求的方法
     * @param args   请求的参数
     */
    private void updateToken(Method method, Object[] args) {
        Annotation[][] annotationsArray = method.getParameterAnnotations();
        Annotation[] annotations;
        if (annotationsArray.length > 0) {
            for (int i = 0; i < annotationsArray.length; i++) {
                annotations = annotationsArray[i];
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Header) {
                        if (TOKEN.equals(((Header) annotation).value())) {
                            args[i] = TempSettingConfig.SP_getToken();
                        }
                    }
                }
            }
        }
    }

}
