package cn.hayring.caseanalyst.activity.ListActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.hayring.caseanalyst.activity.ValueSetter.CaseValueSetter;
import cn.hayring.caseanalyst.activity.ValueSetter.ValueSetter;
import cn.hayring.caseanalyst.pojo.Case;

public class CaseListActivity extends MyListActivity<Case> {


    Handler handler;

    /***
     * 获得本Activity所对应的元素类型
     * @return
     */
    @Override
    public Class<Case> getTClass() {
        return Case.class;
    }

    /***
     * 获得本Activity所对应的ValueSetter
     * @return
     */
    @Override
    public Class<CaseValueSetter> getValueSetterClass() {
        return CaseValueSetter.class;
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        //原始数据添加
        //Case item = cn.hayring.caseanalyst.pojo.PojoInstanceCreater.getConanCase();
        //mainItemListAdapter.addItem(item);
        handler = new SaveHandler();
        Intent instance = getIntent();
        ArrayList<Case> items = (ArrayList<Case>) instance.getSerializableExtra("DATA");
        mainItemListAdapter.addAllItem(items);


        ////-----------------------------debug code
        ValueSetter.list = (ArrayList<Case>) mainItemListAdapter.getItems();

    }


    /***
     * 保存案件
     */
    @Override
    public void finish() {
        new SaveThread().start();
        super.finish();
    }


    class SaveThread extends Thread {
        @Override
        public void run() {
            Message msg = handler.obtainMessage();
            msg.obj = mainItemListAdapter.getItems();
            msg.arg1 = mainItemListAdapter.getItemCount();
            handler.sendMessage(msg);
        }
    }

    class SaveHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int i = msg.arg1;
            List<Case> caseList = (List<Case>) msg.obj;
            for (int j = 0; j < i; j++) {
                try {
                    FileOutputStream fo = openFileOutput(Integer.toString(j) + ".case", Context.MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(fo);
                    oos.writeObject(caseList.get(j));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
