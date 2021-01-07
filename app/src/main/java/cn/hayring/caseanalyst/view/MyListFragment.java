package cn.hayring.caseanalyst.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.domain.Listable;
import cn.hayring.caseanalyst.utils.Pointer;

public abstract class MyListFragment<T extends Listable> extends Fragment {
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
     * 获得本Activity对应的布局管理器
     */
    public abstract RecyclerView.LayoutManager getLayoutManager();


    /***
     * 获取数据
     */
    protected abstract ArrayList<T> getData();

    public static final int REQUEST_CODE = 1;

    Toolbar toolbar;

    /***
     * 添加元素按钮
     */
    FloatingActionButton createItemButton;

    /***
     * 列表View
     */
    RecyclerView itemListRecycler;
    /***
     * 列表适配器
     */
    MyFragmentListAdapter<T> mainItemListAdapter;

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    /***
     * activity引用
     */
    protected MainActivity mainActivity;


    public RecyclerView getItemListRecycler() {
        return itemListRecycler;
    }

    /***
     * 生命周期加载方法
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    /***
     * 初始化view
     */
    @CallSuper
    protected void initView(View view) {

        mainActivity = (MainActivity) getContext();

        toolbar = view.findViewById(R.id.toolbar);
        mainActivity.setSupportActionBar(toolbar);

        //注册
        createItemButton = view.findViewById(R.id.add_item_button);
        createItemButton.setOnClickListener(new CreateNewItemListener());
        //新建元素未保存前禁用
        createItemButton.setEnabled(mainActivity.isSaved() || !mainActivity.isCreate());
        //初始化数据源
        ArrayList<T> items = getData();
        //绑定数据源
        itemListRecycler = view.findViewById(R.id.recycler_list);
        itemListRecycler.setLayoutManager(getLayoutManager());
        itemListRecycler.setItemAnimator(new DefaultItemAnimator());
        mainItemListAdapter = new MyFragmentListAdapter(mainActivity, this, items);


        itemListRecycler.setAdapter(mainItemListAdapter);/*
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        itemListRecycler.addItemDecoration(dividerItemDecoration);*/


    }

    @Override
    public void onHiddenChanged(boolean isHidden) {
        if (!isHidden && mainActivity.isCreate() && mainActivity.isSaved()) {
            createItemButton.setEnabled(true);
        }
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
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

            Intent itemTransporter = new Intent(mainActivity, getValueSetterClass());
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
    public void onActivityResult(int requestCode, int resultCode, Intent itemTransporter) {
        super.onActivityResult(requestCode, resultCode, itemTransporter);

        //未改变就结束
        if (!itemTransporter.getBooleanExtra(ValueSetter.CHANGED, true)) {
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
}
