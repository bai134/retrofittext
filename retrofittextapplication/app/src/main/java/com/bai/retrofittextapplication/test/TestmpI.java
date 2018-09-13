package com.bai.retrofittextapplication.test;

import com.bai.retrofittextapplication.Utils.RemoteApiFactoryUtils;
import com.bai.retrofittextapplication.bean.TestBean;
import com.bai.retrofittextapplication.config.API;

import java.text.ParseException;

public class TestmpI implements TestI {

    private TestViewI testViewI;

    public TestmpI(TestViewI testViewI){
        this.testViewI = testViewI;
    }

    @Override
    public void Test(String s) {
        if (testViewI!=null&&testViewI.checkNetWork()==NetType.NET_DISABLED)
            testViewI.onError(ErrorCode.ERROR_FAILED,"网络异常");
        else {
            if (testViewI!=null)
                testViewI.showPro();
            RemoteApiFactoryUtils.executeMethod(RemoteApiFactoryUtils.createRemoteApi(API.class).Test(s), new RemoteApiFactoryUtils.OnCallBack<TestBean>() {
                @Override
                public void onSucceed(TestBean data) {
                    if (testViewI!=null) {
                        testViewI.onTest(data);
                        //testViewI.toast("....");
                    }
                }

                @Override
                public void onComplete() {
                    if (testViewI!=null)
                        testViewI.dismissPro();
                }

                @Override
                public void onError(Throwable e) {
                    if (testViewI!=null) {
                        if (e instanceof ParseException){
                            //testViewI.onError(.......);
                        }//else ...
                        testViewI.showConntectError();
                        testViewI.dismissPro();
                    }
                }
            });
        }
    }

    @Override
    public void Test_post(String s) {
        if (testViewI!=null&&testViewI.checkNetWork()==NetType.NET_DISABLED)
            testViewI.onError(ErrorCode.ERROR_FAILED,"网络异常");
        else {
            if (testViewI!=null)
                testViewI.showPro();
            RemoteApiFactoryUtils.executeMethod(RemoteApiFactoryUtils.createRemoteApi(API.class).Test_post(s), new RemoteApiFactoryUtils.OnCallBack<TestBean>() {
                @Override
                public void onSucceed(TestBean data) {
                    if (testViewI!=null) {
                        testViewI.onTest(data);
                        //testViewI.toast("....");
                    }
                }

                @Override
                public void onComplete() {
                    if (testViewI!=null)
                        testViewI.dismissPro();
                }

                @Override
                public void onError(Throwable e) {
                    if (testViewI!=null) {
                        if (e instanceof ParseException){
                            //testViewI.onError(.......);
                        }//else ...
                        testViewI.showConntectError();
                        testViewI.dismissPro();
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }
}
