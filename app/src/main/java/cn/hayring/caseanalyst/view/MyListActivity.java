package cn.hayring.caseanalyst.view;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import java.util.ArrayList;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.domain.Listable;
import cn.hayring.caseanalyst.utils.Pointer;
import cn.hayring.caseanalyst.view.caselist.CaseListActivity;

/***
 * 自定义ListActivity
 * @param <T extends Listable>
 */
public abstract class MyListActivity<T extends Listable> extends AppCompatActivity {
    /***
     * 获得本Activity所对应的元素类型
     * @return
     */
    public abstract Class<T> getTClass();

    /***
     * 获取元素布局id
     * @return
     */
    public abstract int getSingleLayoutId();

    /***
     * 获得本Activity所对应的元素类型
     * @return
     */
    public abstract Class getValueSetterClass();

    /***
     * 请求传输者
     */
    protected Intent requestInfo;


    public static final int REQUEST_CODE = 1;


    protected Toolbar toolbar;

    /***
     * 添加元素按钮
     */
    protected FloatingActionButton createItemButton;

    /***
     * 列表View
     */
    protected RecyclerView itemListRecycler;

    /***
     * 列表适配器
     */
    protected AbstractListAdapter<T> mainItemListAdapter;

    public RecyclerView getItemListRecycler() {
        return itemListRecycler;
    }

    /***
     * 生命周期加载方法
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(this instanceof CaseListActivity)) {
            setContentView(R.layout.activity_list);
            initView();
        }
    }

    /***
     * 初始化view
     */
    protected void initView() {


        //注册
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createItemButton = findViewById(R.id.add_item_button);
        createItemButton.setOnClickListener(new CreateNewItemListener());
        //初始化数据源
        ArrayList<T> items = (ArrayList) Pointer.getPoint();
        //绑定数据源
        itemListRecycler = findViewById(R.id.recycler_list);
        itemListRecycler.setLayoutManager(new LinearLayoutManager(this));
        itemListRecycler.setItemAnimator(new DefaultItemAnimator());
        mainItemListAdapter = new MyListAdapter(this, items);


        itemListRecycler.setAdapter(mainItemListAdapter);/*
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        itemListRecycler.addItemDecoration(dividerItemDecoration);*/


        requestInfo = getIntent();

        /*
        //原始数据添加
        T item = cn.hayring.caseanalyst.pojo.PojoInstanceCreater.getConanCase();
        mainRelationshipListAdapter.addItem(item);
        */
    }


    /***
     * 新元素点击监听器
     */
    public class CreateNewItemListener implements View.OnClickListener {

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
            startActivityForResult(itemTransporter, REQUEST_CODE);

        }
    }


    /***
     * 编辑完成调用
     * @author Hayring
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent itemTransporter) {
        super.onActivityResult(requestCode, resultCode, itemTransporter);

        //未改变就结束
        if (!itemTransporter.getBooleanExtra(ValueSetter.CHANGED, true) || this instanceof CaseListActivity) {
            return;
        }

        if (itemTransporter.getBooleanExtra(ValueSetter.CREATE_OR_NOT, false)) {
            //新元素
            //T newItem = (T) itemTransporter.getSerializableExtra(ValueSetter.DATA);
            T newItem = (T) Pointer.getPoint();
            //内部已实现Notificate UI 变化
            mainItemListAdapter.addItem(newItem);
        } else if (!itemTransporter.getBooleanExtra(ValueSetter.CREATE_OR_NOT, true)) {
            //修改元素
            //int position = itemTransporter.getIntExtra(ValueSetter.POSITION, 0);
            //T newItem = (T) itemTransporter.getSerializableExtra(ValueSetter.DATA);
            //mainItemListAdapter.setItem(position, newItem);

            //改变了内部引用，直接检测数据源变化
            mainItemListAdapter.notifyDataSetChanged();
        }


    }

    protected void superOnCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
