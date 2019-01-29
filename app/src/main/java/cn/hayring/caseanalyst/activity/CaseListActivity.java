package cn.hayring.caseanalyst.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.adapter.CaseListAdapter;
import cn.hayring.caseanalyst.pojo.Case;

public class CaseListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_list);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        List<Case> cases = new ArrayList<Case>();

        RecyclerView caseList = findViewById(R.id.case_list);
        caseList.setLayoutManager(new LinearLayoutManager(this));
        caseList.setItemAnimator(new DefaultItemAnimator());
        CaseListAdapter mainCaseListAdapter = new CaseListAdapter(this, cases);
        caseList.setAdapter(mainCaseListAdapter);
        Case caseInstance = cn.hayring.caseanalyst.pojo.PojoInstanceCreater.getConanCase();
        mainCaseListAdapter.addNewItem(caseInstance);

    }

}
