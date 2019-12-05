package com.bai.retrofittextapplication.test;


import com.qcsoft.baselibrary.bean.TestBean;
import com.qcsoft.baselibrary.bean.TokenBean;
import com.qcsoft.netlibrary.mviewi.BaseViewI;

public interface TestViewI extends BaseViewI {
    void onTest(TestBean data);
    void onToken(TokenBean data);
}
