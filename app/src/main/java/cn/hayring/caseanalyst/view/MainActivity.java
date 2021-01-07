package cn.hayring.caseanalyst.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.domain.Case;
import cn.hayring.caseanalyst.utils.Pointer;
import cn.hayring.caseanalyst.view.casemanager.EventListFragment;
import cn.hayring.caseanalyst.view.casemanager.EvidenceListFragment;
import cn.hayring.caseanalyst.view.casemanager.InfoSetterFragment;
import cn.hayring.caseanalyst.view.casemanager.OrgListFragment;
import cn.hayring.caseanalyst.view.casemanager.PersonListFragment;

public class MainActivity extends AppCompatActivity {

    /***
     * 五个Fragment
     */
    protected Fragment persons;
    protected Fragment events;
    protected Fragment evidences;
    protected Fragment organizations;
    protected Fragment info;
    protected Fragment lastFragment;

    protected boolean isCreate;

    protected boolean isSaved;

    protected Intent requestInfo;

    protected BottomNavigationView bottomNavigationView;


    protected Case caseInstance;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.person_list:
                    switchFragment(persons);
                    return true;
                case R.id.event_list:
                    switchFragment(events);
                    return true;
                case R.id.evidence_list:
                    switchFragment(evidences);
                    return true;
                case R.id.org_list:
                    switchFragment(organizations);
                    return true;
                case R.id.info_settings:
                    switchFragment(info);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestInfo = getIntent();
        isCreate = requestInfo.getBooleanExtra(ValueSetter.CREATE_OR_NOT, true);

        //获取案件数据
        if (!isCreate) {
            caseInstance = (Case) Pointer.getPoint();
            ValueSetter.setCaseInstance(caseInstance);
        } else {
            caseInstance = new Case();
            ValueSetter.setCaseInstance(caseInstance);
        }
        //caseInstance = PojoInstanceCreater.getConanCase();
        initView();
        initFragment();
    }

    protected void initView() {
        bottomNavigationView = findViewById(R.id.navigation);
    }

    private void initFragment() {

        persons = new PersonListFragment();
        events = new EventListFragment();
        evidences = new EvidenceListFragment();
        organizations = new OrgListFragment();
        info = new InfoSetterFragment();
        if (isCreate) {
            lastFragment = info;
            getSupportFragmentManager().beginTransaction().replace(R.id.mainview, info).show(info).commit();
            bottomNavigationView.setSelectedItemId(R.id.info_settings);
        } else {
            lastFragment = persons;
            getSupportFragmentManager().beginTransaction().replace(R.id.mainview, persons).show(persons).commitNow();
        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    private void switchFragment(Fragment nextfragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(lastFragment);//隐藏上个Fragment
        if (nextfragment.isAdded() == false) {
            transaction.add(R.id.mainview, nextfragment);


        }

        transaction.show(nextfragment).commitAllowingStateLoss();

        lastFragment = nextfragment;
    }


    public Case getCaseInstance() {
        return caseInstance;
    }


    public boolean isCreate() {
        return isCreate;
    }

    public void setCreate(boolean create) {
        isCreate = create;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    @Override
    public void finish() {
        if (isCreate) {
            requestInfo.putExtra(ValueSetter.CHANGED, isSaved);
            if (isSaved) {
                Pointer.setPoint(caseInstance);
            }
        } else {
            if (info.isAdded()) {
                ((InfoSetterFragment) info).writeInstance();
            }
        }
        setResult(2, requestInfo);
        super.finish();
    }
}
