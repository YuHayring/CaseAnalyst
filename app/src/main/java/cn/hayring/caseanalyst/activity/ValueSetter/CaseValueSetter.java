package cn.hayring.caseanalyst.activity.ValueSetter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.ListActivity.EventListActivity;
import cn.hayring.caseanalyst.activity.ListActivity.EvidenceListActivity;
import cn.hayring.caseanalyst.activity.ListActivity.OrganizationListActivity;
import cn.hayring.caseanalyst.activity.ListActivity.PersonListActivity;
import cn.hayring.caseanalyst.pojo.Case;
import cn.hayring.caseanalyst.utils.Pointer;

public class CaseValueSetter extends ValueSetter {

    /***
     * 相关人士列表入口
     */
    TextView personsEnter;

    /***
     * 相关组织列表入口
     */
    TextView orgsEnter;

    /***
     * 事件列表入口
     */
    TextView eventsEnter;

    /***
     * 证物列表入口
     */
    TextView evidenceEnter;

    /***
     * 加载页面
     */
    @Override
    protected void initView() {
        super.initView();
        //加载layout实例
        LayoutInflater inflater = getLayoutInflater();
        sonView = (ScrollView) inflater.inflate(R.layout.case_value_setter, null);
        rootLayout.addView(sonView);

        //注册控件,信息显示
        nameInputer = findViewById(R.id.case_name_inputer);
        infoInputer = findViewById(R.id.case_info_inputer);
        saveButton = findViewById(R.id.case_save_button);
        if (!requestInfo.getBooleanExtra(CREATE_OR_NOT, true)) {

            //caseInstance = (Case) requestInfo.getSerializableExtra(DATA);
            caseInstance = (Case) Pointer.getPoint();


            nameInputer.setText(caseInstance.getName());
            infoInputer.setText(caseInstance.getInfo());
        } else {
            caseInstance = new Case();
        }
        personsEnter = findViewById(R.id.person_list_enter);
        orgsEnter = findViewById(R.id.org_list_enter);
        eventsEnter = findViewById(R.id.event_list_enter);
        evidenceEnter = findViewById(R.id.evidence_list_enter);

        //设置监听器
        saveButton.setOnClickListener(new CaseFinishEditListener());
        personsEnter.setOnClickListener(new PersonsEnterListener(this));
        orgsEnter.setOnClickListener(new OrgsEnterListener(this));
        eventsEnter.setOnClickListener(new EventEnterListener(this));
        evidenceEnter.setOnClickListener(new EvidenceEnterListener(this));



    }

    /***
     * 保存按钮监听器
     */
    class CaseFinishEditListener extends FinishEditListener {
        @Override
        void editReaction() {
            caseInstance.setName(nameInputer.getText().toString());
            caseInstance.setInfo(infoInputer.getText().toString());
            //requestInfo.putExtra(DATA, caseInstance);
            requestInfo.putExtra(CHANGED, true);
            if (requestInfo.getBooleanExtra(CREATE_OR_NOT, false)) {
                Pointer.setPoint(caseInstance);
            }
        }
    }

    /***
     * 相关人士列表入口按钮监听器
     *
     */
    class PersonsEnterListener extends ListEnterListener {

        public PersonsEnterListener(Context packageContext) {
            super(packageContext);
        }

        @Override
        Class getListViewClass() {
            return PersonListActivity.class;
        }


        @Override
        Serializable getList() {
            return caseInstance.getPersons();
        }
    }

    /***
     * 相关组织列表入口按钮监听器
     *
     */
    class OrgsEnterListener extends ListEnterListener {

        public OrgsEnterListener(Context packageContext) {
            super(packageContext);
        }

        @Override
        Class getListViewClass() {
            return OrganizationListActivity.class;
        }


        @Override
        Serializable getList() {
            return caseInstance.getOrganizations();
        }
    }

    /***
     * 事件列表入口按钮监听器
     */
    class EventEnterListener extends ListEnterListener {

        public EventEnterListener(Context packageContext) {
            super(packageContext);
        }

        @Override
        Class getListViewClass() {
            return EventListActivity.class;
        }


        @Override
        Serializable getList() {
            return caseInstance.getEvents();
        }
    }

    /***
     * 证物列表入口按钮监听器
     */
    class EvidenceEnterListener extends ListEnterListener {

        public EvidenceEnterListener(Context packageContext) {
            super(packageContext);
        }

        @Override
        Class getListViewClass() {
            return EvidenceListActivity.class;
        }


        @Override
        Serializable getList() {
            return caseInstance.getEvidences();
        }
    }

    /***
     * 编辑完成调用
     * @author Hayring
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent itemTransporter) {
        super.onActivityResult(requestCode, resultCode, itemTransporter);
        int type = itemTransporter.getIntExtra(TYPE, -1);
        ArrayList data = (ArrayList) Pointer.getPoint();
        //设置返回的已修改数据
        switch (type) {
            case PERSON_LIST: {
                //ArrayList<Person> persons = (ArrayList<Person>) itemTransporter.getSerializableExtra(DATA);
                caseInstance.setPersons(data);
            }
            break;
            case ORG_LIST: {
                //ArrayList<Organization> orgs = (ArrayList<Organization>) itemTransporter.getSerializableExtra(DATA);
                caseInstance.setOrganizations(data);
            }
            break;
            case EVENT_LIST: {
                //ArrayList<Event> events = (ArrayList<Event>) itemTransporter.getSerializableExtra(DATA);
                caseInstance.setEvents(data);
            }
            break;
            case EVIDENCE_LIST: {
                //ArrayList<Evidence> evidences = (ArrayList<Evidence>) itemTransporter.getSerializableExtra(DATA);
                caseInstance.setEvidences(data);
            }
            break;
            default:
                throw new IllegalArgumentException("Error type!");
        }


    }


    /***
     * 返回键不保存，返回未改动
     */
    @Override
    public void finish() {
        requestInfo.putExtra(CHANGED, false);
        setResult(2, requestInfo);
        super.finish();
    }




}
