package cn.hayring.caseanalyst.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.ListActivity.CaseListActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //隐藏标题栏
        getSupportActionBar().hide();

        setContentView(R.layout.activity_splash);

        Thread myThread = new Thread() {//创建子线程
            @Override
            public void run() {
                try {
                    //使程序休眠1秒
                    sleep(1000);
                    Intent it = new Intent(getApplicationContext(), CaseListActivity.class);
                    startActivity(it);

                    //关闭当前活动
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }


}

