package cn.hayring.caseanalyst.activity.ListActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.ValueSetter.CaseValueSetter;
import cn.hayring.caseanalyst.activity.ValueSetter.ValueSetter;
import cn.hayring.caseanalyst.activity.adapter.MyListAdapter;
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

        //------------------
        setContentView(R.layout.activity_list);
        //注册
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createItemButton = findViewById(R.id.add_item_button);
        createItemButton.setOnClickListener(new CreateNewItemListener());
        //初始化数据源
        List<Case> items = new ArrayList<Case>();
        //绑定数据源
        itemListRecycler = findViewById(R.id.recycler_list);
        itemListRecycler.setLayoutManager(new LinearLayoutManager(this));
        itemListRecycler.setItemAnimator(new DefaultItemAnimator());
        mainItemListAdapter = new MyListAdapter(this, items);


        itemListRecycler.setAdapter(mainItemListAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        itemListRecycler.addItemDecoration(dividerItemDecoration);
        //-----------------------


        //原始数据添加
        //Case item = cn.hayring.caseanalyst.pojo.PojoInstanceCreater.getConanCase();
        //mainItemListAdapter.addItem(item);
        handler = new SaveHandler();
        Intent instance = getIntent();
        ArrayList<Case> data = (ArrayList<Case>) instance.getSerializableExtra("DATA");
        mainItemListAdapter.addAllItem(data);


        ////-----------------------------debug code
        ValueSetter.list = (ArrayList<Case>) mainItemListAdapter.getItems();

    }

    /***
     * 注册菜单方法
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.case_list_menu, menu);
        return true;
    }

    /***
     * 菜单点击监听器
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_all_case_button) {
            new SaveThread().start();
        } else if (item.getItemId() == R.id.create_example_button) {
            Case caseInstance = cn.hayring.caseanalyst.pojo.PojoInstanceCreater.getConanCase();
            mainItemListAdapter.addItem(caseInstance);
        }

        return super.onOptionsItemSelected(item);
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
