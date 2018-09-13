package com.bai.retrofittextapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bai.retrofittextapplication.bean.TestBean;
import com.bai.retrofittextapplication.test.ErrorCode;
import com.bai.retrofittextapplication.test.NetType;
import com.bai.retrofittextapplication.test.TestViewI;
import com.bai.retrofittextapplication.test.TestmpI;

public class MainActivity extends AppCompatActivity {

    private TextView tv_test;
    private Button button;

    private TestViewI testViewI;
    private TestmpI testmpI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_test = findViewById(R.id.textView2);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testmpI.Test("123");
            }
        });

        initView();
    }

    private void initView() {
        testViewI = new TestViewI() {
            @Override
            public void onTest(TestBean data) {
                if (data.getError().equals("0")){
                    Toast.makeText(MainActivity.this,"处理接口返回数据",Toast.LENGTH_SHORT).show();
                }else {

                }
            }

            @Override
            public NetType checkNetWork() {
                return null;
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
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showConntectError() {
                //显示错误信息
            }

            @Override
            public void onError(ErrorCode code, String message) {
                //发生错误时调用
            }
        };
        testmpI = new TestmpI(testViewI);
    }
}
