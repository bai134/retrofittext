package com.qcsoft.netlibrary.factory;


import com.qcsoft.baselibrary.bean.TokenBean;

import io.reactivex.Flowable;
import retrofit2.http.POST;

public interface TokenAPI {

    String http = "";

    //获取token
    @POST("")
    Flowable<TokenBean> getToken();
}
