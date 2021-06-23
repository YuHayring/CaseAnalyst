package cn.hayring.caseanalyst.view.caselist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.databinding.ActivityListBinding;
import cn.hayring.caseanalyst.domain.Case;
import cn.hayring.caseanalyst.view.MyListActivity;
import cn.hayring.caseanalyst.view.casemanager.CaseManagerActivity;

/**
 * @author hayring
 * @date 6/21/21 5:40 PM
 */
public class CaseListActivity extends MyListActivity<Case> {


    ActivityListBinding viewBinding;

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
        return CaseManagerActivity.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityListBinding.inflate(LayoutInflater.from(this));
        setContentView(viewBinding.getRoot());
        initView();

    }


    ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            try {
                Constructor constructor = modelClass.getConstructor();
                return (T) constructor.newInstance();
            } catch (Exception e) {
                IllegalArgumentException ile = new IllegalArgumentException("" + modelClass + " is not" + CaseViewModel.class);
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
        viewBinding.addItemButton.setOnClickListener(new CreateNewItemListener());
        //初始化数据源
        List<Case> items = new ArrayList<Case>();
        //绑定数据源
        viewBinding.contentList.recyclerList.setLayoutManager(new LinearLayoutManager(this));
        viewBinding.contentList.recyclerList.setItemAnimator(new DefaultItemAnimator());
        mainItemListAdapter = new CaseListAdapter(this, items);


        viewBinding.contentList.recyclerList.setAdapter(mainItemListAdapter);


        caseViewModel = new ViewModelProvider(this, factory).get(CaseViewModel.class);
        caseViewModel.getCaseListData().observe(this, caseListObserver);
        caseViewModel.getCaseList();
    }

    private final Observer<List<Case>> caseListObserver = new Observer<List<Case>>() {
        @Override
        public void onChanged(List<Case> cases) {
            mainItemListAdapter.deleteAll();
            mainItemListAdapter.addAllItem(cases);
        }
    };

    public CaseViewModel getCaseViewModel() {
        return caseViewModel;
    }

    /***
     * 编辑完成调用
     * @author Hayring
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent itemTransporter) {
        super.onActivityResult(requestCode, resultCode, itemTransporter);
        caseViewModel.getCaseList();

    }
}
