package cn.hayring.caseanalyst.activity;

import android.content.Intent;
import android.os.Bundle;
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
import cn.hayring.caseanalyst.activity.adapter.CaseListAdapter;
import cn.hayring.caseanalyst.pojo.Case;

public class CaseListActivity extends AppCompatActivity {
    public static final int REQUESTCODE = 1;
    Toolbar toolbar;
    FloatingActionButton createCaseButton;
    RecyclerView caseList;
    CaseListAdapter mainCaseListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createCaseButton = (FloatingActionButton) findViewById(R.id.add_case_button);
        createCaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        List<Case> cases = new ArrayList<Case>();

        caseList = findViewById(R.id.recycler_list);
        caseList.setLayoutManager(new LinearLayoutManager(this));
        caseList.setItemAnimator(new DefaultItemAnimator());
        mainCaseListAdapter = new CaseListAdapter(this, cases);
        caseList.setAdapter(mainCaseListAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        caseList.addItemDecoration(dividerItemDecoration);


        Case caseInstance = cn.hayring.caseanalyst.pojo.PojoInstanceCreater.getConanCase();
        mainCaseListAdapter.addNewItem(caseInstance);
        createCaseButton.setOnClickListener(new CreateNewCaseListener());


    }


    class CreateNewCaseListener implements View.OnClickListener {

        /***
         * 创建新案件
         * Create new Case
         * @param view
         */
        @Override
        public void onClick(View view) {
            Intent caseTransporter = new Intent(CaseListActivity.this, ValueSetter.class);
            caseTransporter.putExtra(ValueSetter.TYPE, ValueSetter.CASE);
            caseTransporter.putExtra(ValueSetter.CREATE_OR_NOT, true);
            startActivityForResult(caseTransporter, REQUESTCODE);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent caseTransporter) {
        super.onActivityResult(requestCode, resultCode, caseTransporter);
        Case newCase = (Case) caseTransporter.getSerializableExtra(ValueSetter.NEW_CASE);
        //内部已实现Notificate UI 变化
        mainCaseListAdapter.addNewItem(newCase);
    }


}
