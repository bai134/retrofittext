package com.bai.retrofittextapplication.config;

import com.bai.retrofittextapplication.bean.TestBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    String http = "";

    @GET(http+"")
    Flowable<TestBean> Test(@Query("sss")String sss);

    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    @FormUrlEncoded
    @POST(http)
    Flowable<TestBean> Test_post(@Field("sss")String sss);


}
