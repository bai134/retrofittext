package com.bai.retrofittextapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.bai.retrofittextapplication.test.TestViewI;
import com.bai.retrofittextapplication.test.TestmpI;
import com.qcsoft.baselibrary.bean.TestBean;
import com.qcsoft.baselibrary.bean.TokenBean;
import com.qcsoft.baselibrary.utils.TempSettingConfig;
import com.qcsoft.netlibrary.nettype.ErrorCode;
import com.qcsoft.netlibrary.nettype.NetType;
import com.qcsoft.netlibrary.nettype.TempNetUtils;

public class MainActivity extends AppCompatActivity {

    private TestmpI testmpI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_test = findViewById(R.id.textView2);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> testmpI.Test("123"));

        initView();
    }

    private void initView() {
        TestViewI testViewI = new TestViewI() {
            @Override
            public void onTest(TestBean data) {
                if (data.getError().equals("0")) {
                    Toast.makeText(MainActivity.this, "处理接口返回数据", Toast.LENGTH_SHORT).show();
                } else {

                }
            }

            @Override
            public void onToken(TokenBean data) {
//                TempSettingConfig.SP_saveToken("");保存token
            }

            @Override
            public NetType checkNetWork() {
                return TempNetUtils.getNetType(MainActivity.this);
            }

            @Override
            public void showPro() {
                //等待请求加载
            }

            @Override
            public void dismissPro() {
                //等待请求加载消失
            }

            @Override
            public void toast(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showConntectError(String requestFlag) {
                //显示错误信息
                //                if (requestFlag.equals("Test"))
            }

            @Override
            public void onError(ErrorCode code, String message) {
                //发生错误时调用
            }
        };
        testmpI = new TestmpI(testViewI);

        testmpI.getToken();
    }
}
