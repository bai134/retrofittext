package com.bai.retrofittextapplication.test;

public interface BaseViewI {
    NetType checkNetWork();

    void showPro();
    void dismissPro();
    void toast(String message);
    void showConntectError();
    void onError(ErrorCode code, String message);

}
