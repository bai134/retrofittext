package com.qcsoft.baselibrary.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.qcsoft.baselibrary.application.RetrofitTextApplication;


/**
 * Cerated by bailing
 * Create date : 2019/6/12 9:29
 * description :
 */

public class TempSettingConfig {
    public final static String TAG = "SPSettingConfig";

    public final static String SETTING_INFO = "Setting_info";//保存设备信息

    public final static String TOKEN;//token
    public final static String TOKEN_TIME;//请求token时的时间


    static {

        TOKEN = "token";
        TOKEN_TIME = "token_time";

    }


    public static void SP_saveTokenTime(String tokenTime) {
        SharedPreferences sf = RetrofitTextApplication.getInstance().getApplicationContext()
                .getSharedPreferences(SETTING_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        editor.putString(TOKEN_TIME, tokenTime);
        editor.commit();
    }

    public static String SP_getTokenTime() {
        SharedPreferences sf = RetrofitTextApplication.getInstance().getApplicationContext()
                .getSharedPreferences(SETTING_INFO, Context.MODE_PRIVATE);
        return sf.getString(TOKEN_TIME, "");
    }

    public static void SP_saveToken(String token) {
        SharedPreferences sf = RetrofitTextApplication.getInstance().getApplicationContext()
                .getSharedPreferences(SETTING_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        editor.putString(TOKEN, token);
        editor.commit();
    }

    public static String SP_getToken() {
        SharedPreferences sf = RetrofitTextApplication.getInstance().getApplicationContext()
                .getSharedPreferences(SETTING_INFO, Context.MODE_PRIVATE);
        return sf.getString(TOKEN, "");
    }



}

