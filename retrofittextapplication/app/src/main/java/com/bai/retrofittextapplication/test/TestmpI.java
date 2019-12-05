package com.bai.retrofittextapplication.test;

import com.bai.retrofittextapplication.config.API;
import com.qcsoft.baselibrary.bean.TestBean;
import com.qcsoft.baselibrary.bean.TokenBean;
import com.qcsoft.netlibrary.factory.RemoteApiFactoryUtils;
import com.qcsoft.netlibrary.nettype.ErrorCode;
import com.qcsoft.netlibrary.nettype.NetType;

import java.text.ParseException;

public class TestmpI implements TestI {

    private TestViewI testViewI;

    public TestmpI(TestViewI testViewI){
        this.testViewI = testViewI;
    }

    @Override
    public void Test(String s) {
        String requestFlag = "test";
        if (testViewI!=null&&testViewI.checkNetWork()== NetType.NET_DISABLED)
            testViewI.onError(ErrorCode.ERROR_FAILED,"网络异常");
        else {
            if (testViewI!=null)
                testViewI.showPro();
            RemoteApiFactoryUtils.getInstance().executeMethod(requestFlag,RemoteApiFactoryUtils.getInstance().createRemoteApi(API.class).Test(s), new RemoteApiFactoryUtils.OnCallBack<TestBean>() {
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
                        testViewI.showConntectError(requestFlag);
                        testViewI.dismissPro();
                    }
                }
            });
        }
    }

    @Override
    public void Test_post(String s) {
        String requestFlag = "test_post";
        if (testViewI!=null&&testViewI.checkNetWork()==NetType.NET_DISABLED)
            testViewI.onError(ErrorCode.ERROR_FAILED,"网络异常");
        else {
            if (testViewI!=null)
                testViewI.showPro();
            RemoteApiFactoryUtils.getInstance().executeMethod(requestFlag,RemoteApiFactoryUtils.getInstance().createRemoteApi(API.class).Test_post(s), new RemoteApiFactoryUtils.OnCallBack<TestBean>() {
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
                        testViewI.showConntectError(requestFlag);
                        testViewI.dismissPro();
                    }
                }
            });
        }
    }

    @Override
    public void getToken() {
        String requestFlag = "getToken";
        if (testViewI!=null&&testViewI.checkNetWork()==NetType.NET_DISABLED)
            testViewI.onError(ErrorCode.ERROR_FAILED,"网络异常");
        else {
            if (testViewI!=null)
                testViewI.showPro();
            RemoteApiFactoryUtils.getInstance().executeMethod(requestFlag,RemoteApiFactoryUtils.getInstance().createRemoteApi(API.class).getToken(), new RemoteApiFactoryUtils.OnCallBack<TokenBean>() {
                @Override
                public void onSucceed(TokenBean data) {
                    if (testViewI!=null) {
                        testViewI.onToken(data);
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
                        testViewI.showConntectError(requestFlag);
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
