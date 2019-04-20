package cn.hayring.caseanalyst.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.ListActivity.CaseListActivity;
import cn.hayring.caseanalyst.pojo.Case;

public class Splash extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //隐藏标题栏
        getSupportActionBar().hide();

        setContentView(R.layout.activity_splash);

/*        Thread myThread = new Thread() {//创建子线程
            @Override
            public void run() {
                try {
                    //使程序休眠1秒

                    sleep(1000);

                    //关闭当前活动
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };*/
        handler = new ReadHandler();
        handler.sendEmptyMessage(0);
        //new ReadThread().start();
    }


/*    class ReadThread extends Thread {
        @Override
        public void run() {
            Message msg = handler.obtainMessage();
            handler.sendMessage(msg);
            try {
                //使程序休眠1秒

                sleep(1000);

                //关闭当前活动
                finish();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }*/

    class ReadHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            ArrayList<Case> caseList = new ArrayList();
            int i = 0;
            FileInputStream fi;
            ObjectInputStream ois;
            while (true) {
                try {
                    fi = openFileInput(Integer.toString(i++) + ".case");
                    ois = new ObjectInputStream(fi);
                    caseList.add((Case) ois.readObject());
                } catch (FileNotFoundException e) {
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(Splash.this);
                    builder.setTitle("错误！");
                    builder.setMessage("数据版本过旧，请在系统设置清除所有数据再启动。");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            throw new RuntimeException(e);
                        }
                    });
                    builder.show();
                    return;
                }


            }

            Intent it = new Intent(getApplicationContext(), CaseListActivity.class);
            it.putExtra("DATA", caseList);
            startActivity(it);
            finish();
        }
    }


}

