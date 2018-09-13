package com.bai.retrofittextapplication.test;

import com.bai.retrofittextapplication.bean.TestBean;

public interface TestViewI extends BaseViewI {
    void onTest(TestBean data);
}
