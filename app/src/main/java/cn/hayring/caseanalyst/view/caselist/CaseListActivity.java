package cn.hayring.caseanalyst.view.caselist;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.domain.Case;
import cn.hayring.caseanalyst.view.MainActivity;
import cn.hayring.caseanalyst.view.MyListActivity;
import cn.hayring.caseanalyst.view.MyListAdapter;

/**
 * @author hayring
 * @date 6/21/21 5:40 PM
 */
public class CaseListActivity extends MyListActivity<Case> {

    SQLiteDatabase caseDb;

    CaseViewModel caseViewModel;


    @Override
    public Class<Case> getTClass() {
        return Case.class;
    }

    @Override
    public int getSingleLayoutId() {
        return R.layout.single_background_frame;
    }

    @Override
    public Class getValueSetterClass() {
        return MainActivity.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    ViewModelProvider.Factory videoViewModelFactory = new ViewModelProvider.Factory() {

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            try {
                Constructor constructor = modelClass.getConstructor(Activity.class);
                return (T) constructor.newInstance(CaseListActivity.this);
            } catch (Exception e) {
                IllegalArgumentException ile = new IllegalArgumentException("" + modelClass + "is not" + CaseViewModel.class);
                ile.initCause(e);
                throw ile;
            }

        }
    };


    /***
     * 重写初始化view
     */
    @Override
    protected void initView() {
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


        caseViewModel = new ViewModelProvider(this, videoViewModelFactory).get(CaseViewModel.class);
        caseViewModel.caseListData.observe(this, caseListObserver);
    }

    private final Observer<List<Case>> caseListObserver = new Observer<List<Case>>() {
        @Override
        public void onChanged(List<Case> cases) {
            mainItemListAdapter.addAllItem(cases);
        }
    };


}
