package cn.hayring.caseanalyst.view.casemanager.old;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.domain.Case;
import cn.hayring.caseanalyst.utils.Pointer;
import cn.hayring.caseanalyst.view.ValueSetter;

public class CaseValueSetter extends ValueSetter<Case> {


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
     * 短期案件设置器
     */
    Spinner shortTimeCaseSetter;

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
        shortTimeCaseSetter = findViewById(R.id.short_time_case_switcher);

        personsEnter = findViewById(R.id.person_list_enter);
        orgsEnter = findViewById(R.id.org_list_enter);
        eventsEnter = findViewById(R.id.event_list_enter);
        evidenceEnter = findViewById(R.id.evidence_list_enter);

        if (!isCreate) {
            saveButton.setEnabled(false);
            saveButton.setVisibility(View.GONE);
            //instance = (Case) requestInfo.getSerializableExtra(DATA);

            //ValueSetter静态变量，全局可用
            instance = (Case) Pointer.getPoint();
            caseInstance = instance;


            nameInputer.setText(instance.getName());
            infoInputer.setText(instance.getInfo());
            shortTimeCaseSetter.setSelection(instance.isShortTimeCase() ? 0 : 1);
        } else {

            personsEnter.setVisibility(View.GONE);
            eventsEnter.setVisibility(View.GONE);
            evidenceEnter.setVisibility(View.GONE);
            orgsEnter.setVisibility(View.GONE);
            instance = new Case();
        }

        //设置监听器
        saveButton.setOnClickListener(new FinishEditListener());
        personsEnter.setOnClickListener(new PersonsEnterListener(this));
        orgsEnter.setOnClickListener(new OrgsEnterListener(this));
        eventsEnter.setOnClickListener(new EventEnterListener(this));
        evidenceEnter.setOnClickListener(new EvidenceEnterListener(this));



    }

    /***
     * 写入
     */
    @Override
    protected void writeInstance() {
        instance.setName(nameInputer.getText().toString());
        instance.setInfo(infoInputer.getText().toString());
        int index = shortTimeCaseSetter.getSelectedItemPosition();
        if (index == 0) {
            instance.setShortTimeCase(true);
        } else if (index == 1) {
            instance.setShortTimeCase(false);
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
        protected Class getListViewClass() {
            return PersonListActivity.class;
        }


        @Override
        protected Serializable getList() {
            return instance.getPersons();
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
        protected Class getListViewClass() {
            return OrganizationListActivity.class;
        }


        @Override
        protected Serializable getList() {
            return instance.getOrganizations();
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
        protected Class getListViewClass() {
            return EventListActivity.class;
        }


        @Override
        protected Serializable getList() {
            return instance.getEvents();
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
        protected Class getListViewClass() {
            return EvidenceListActivity.class;
        }


        @Override
        protected Serializable getList() {
            return instance.getEvidences();
        }
    }








}
