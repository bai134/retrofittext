package com.qcsoft.netlibrary.mviewi;

import com.qcsoft.netlibrary.nettype.ErrorCode;
import com.qcsoft.netlibrary.nettype.NetType;

public interface BaseViewI {
    NetType checkNetWork();

    void showPro();
    void dismissPro();
    void toast(String message);
    void showConntectError(String requestFlag);
    void onError(ErrorCode code, String message);

}
