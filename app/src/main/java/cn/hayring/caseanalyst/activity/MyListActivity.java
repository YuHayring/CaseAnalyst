package cn.hayring.caseanalyst.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.adapter.MyListAdapter;
import cn.hayring.caseanalyst.pojo.Listable;

public abstract class MyListActivity<T extends Listable> extends AppCompatActivity {
    public abstract Class<T> getTClass();

    public abstract Class getValueSetterClass();


    protected Intent requestInfo;


    public static final int REQUESTCODE = 1;
    Toolbar toolbar;
    //添加元素按钮
    FloatingActionButton createItemButton;
    //列表View
    RecyclerView itemListRecycler;
    //列表适配器
    MyListAdapter<T> mainItemListAdapter;

    public RecyclerView getItemListRecycler() {
        return itemListRecycler;
    }

    /***
     * 必须再次重写以补充初始化后的操作
     * @param savedInstanceState
     */
    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //注册
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createItemButton = findViewById(R.id.add_item_button);
        createItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //初始化数据源
        List<T> items = new ArrayList<T>();
        //绑定数据源
        itemListRecycler = findViewById(R.id.recycler_list);
        itemListRecycler.setLayoutManager(new LinearLayoutManager(this));
        itemListRecycler.setItemAnimator(new DefaultItemAnimator());
        mainItemListAdapter = new MyListAdapter(this, items);
        itemListRecycler.setAdapter(mainItemListAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        itemListRecycler.addItemDecoration(dividerItemDecoration);

        createItemButton.setOnClickListener(new MyListActivity.CreateNewItemListener());

        /*
        //原始数据添加
        T item = cn.hayring.caseanalyst.pojo.PojoInstanceCreater.getConanCase();
        mainItemListAdapter.addItem(item);
        */


    }

    /***
     * 新元素点击监听器
     */
    class CreateNewItemListener implements View.OnClickListener {

        /***
         * 创建新元素
         * Create new Item
         * @param view
         */
        @Override
        public void onClick(View view) {

            Intent itemTransporter = new Intent(MyListActivity.this, getValueSetterClass());
            //行为:新建数据行为
            itemTransporter.putExtra(ValueSetter.CREATE_OR_NOT, true);
            //启动新Activity
            startActivityForResult(itemTransporter, REQUESTCODE);

        }
    }


    /***
     * 编辑完成调用
     * @author Hayring
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent itemTransporter) {
        super.onActivityResult(requestCode, resultCode, itemTransporter);

        if (itemTransporter.getBooleanExtra(ValueSetter.CREATE_OR_NOT, false)) {
            //新元素
            T newItem = (T) itemTransporter.getSerializableExtra(ValueSetter.DATA);
            //内部已实现Notificate UI 变化
            mainItemListAdapter.addItem(newItem);
        } else if (!itemTransporter.getBooleanExtra(ValueSetter.CREATE_OR_NOT, true)) {
            //修改元素
            int position = itemTransporter.getIntExtra(ValueSetter.POSITION, 0);
            T newItem = (T) itemTransporter.getSerializableExtra(ValueSetter.DATA);
            mainItemListAdapter.setItem(position, newItem);
        }


    }
}
