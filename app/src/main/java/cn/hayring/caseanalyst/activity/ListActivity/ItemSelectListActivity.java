package cn.hayring.caseanalyst.activity.ListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.ValueSetter.ValueSetter;
import cn.hayring.caseanalyst.activity.adapter.ItemSelectListAdapter;

public class ItemSelectListActivity extends AppCompatActivity {
    /***
     * 请求传输者
     */
    protected Intent requestInfo;

    public static final int REQUESTCODE = 1;


    Toolbar toolbar;

    /***
     * 列表View
     */
    RecyclerView itemListRecycler;
    /***
     * 列表适配器
     */
    ItemSelectListAdapter mainItemListAdapter;

    public RecyclerView getItemListRecycler() {
        return itemListRecycler;
    }


    /***
     * 必须再次重写以补充初始化后的操作
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_select_list);
        //获取数据传送器
        requestInfo = getIntent();
        //注册
        toolbar = findViewById(R.id.select_list_toolbar);
        setSupportActionBar(toolbar);
        //初始化数据源
        List items = new ArrayList();
        //绑定数据源
        itemListRecycler = findViewById(R.id.recycler_list);
        itemListRecycler.setLayoutManager(new LinearLayoutManager(this));
        itemListRecycler.setItemAnimator(new DefaultItemAnimator());
        mainItemListAdapter = new ItemSelectListAdapter(this, items);


        itemListRecycler.setAdapter(mainItemListAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        itemListRecycler.addItemDecoration(dividerItemDecoration);

        List data = (List) requestInfo.getSerializableExtra(ValueSetter.DATA);
        mainItemListAdapter.addAllItem(data);
        /*
        //原始数据添加
        T item = cn.hayring.caseanalyst.pojo.PojoInstanceCreater.getConanCase();
        mainRelationshipListAdapter.addItem(item);
        */
    }


    @Override
    public void finish() {
        requestInfo.putExtra(ValueSetter.CHANGED, false);
        setResult(2, requestInfo);
        super.finish();
    }

    /***
     * 返回数据
     */
    public void returnIntent() {
        setResult(2, requestInfo);
        super.finish();
    }

    public Intent getRequestInfo() {
        return requestInfo;
    }
}
