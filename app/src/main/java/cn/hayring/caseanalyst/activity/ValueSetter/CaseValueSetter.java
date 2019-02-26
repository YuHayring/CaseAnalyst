package cn.hayring.caseanalyst.activity.ValueSetter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.ListActivity.ActiveUnitListActivity;
import cn.hayring.caseanalyst.activity.ListActivity.EventListActivity;
import cn.hayring.caseanalyst.pojo.ActiveUnit;
import cn.hayring.caseanalyst.pojo.Case;
import cn.hayring.caseanalyst.pojo.Event;

public class CaseValueSetter extends ValueSetter {

    /***
     * 能动单元列表入口
     */
    TextView activeUnitsEnter;

    /***
     * 事件列表入口
     */
    TextView eventsEnter;

    /***
     * 加载页面
     */
    @Override
    public void loadView() {
        //加载layout实例
        LayoutInflater inflater = getLayoutInflater();
        sonView = (ScrollView) inflater.inflate(R.layout.case_value_setter, null);
        rootLayout.addView(sonView);

        //注册控件,信息显示
        nameInputer = findViewById(R.id.case_name_inputer);
        infoInputer = findViewById(R.id.case_info_inputer);
        saveButton = findViewById(R.id.case_save_button);
        if (!requestInfo.getBooleanExtra(CREATE_OR_NOT, true)) {
            caseInstance = (Case) requestInfo.getSerializableExtra(DATA);
            nameInputer.setText(caseInstance.getName());
            infoInputer.setText(caseInstance.getInfo());
        } else {
            caseInstance = new Case();
        }
        activeUnitsEnter = findViewById(R.id.active_unit_list_enter);
        eventsEnter = findViewById(R.id.event_list_enter);

        //设置监听器
        saveButton.setOnClickListener(new CaseFinishEditListener());
        activeUnitsEnter.setOnClickListener(new ActiveUnitsEnterListener(this));
        eventsEnter.setOnClickListener(new EventEnterListener(this));



    }

    /***
     * 保存按钮监听器
     */
    class CaseFinishEditListener extends FinishEditListener {
        @Override
        void editReaction() {
            caseInstance.setName(nameInputer.getText().toString());
            caseInstance.setInfo(infoInputer.getText().toString());
            requestInfo.putExtra(DATA, caseInstance);
            requestInfo.putExtra(CHANGED, true);
        }
    }

    /***
     * 能动单元编辑按钮监听器
     *
     */
    class ActiveUnitsEnterListener extends ListEnterListener {

        public ActiveUnitsEnterListener(Context packageContext) {
            super(packageContext);
        }

        @Override
        Class getListViewClass() {
            return ActiveUnitListActivity.class;
        }


        @Override
        Serializable setData() {
            return caseInstance.getActiveUnits();
        }
    }

    /***
     * 事件编辑监听器
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
        Serializable setData() {
            return caseInstance.getEvents();
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

        //设置返回的已修改数据
        switch (type) {
            case ACTIVE_UNIT_LIST: {
                ArrayList<ActiveUnit> activeUnits = (ArrayList<ActiveUnit>) itemTransporter.getSerializableExtra(DATA);
                caseInstance.setActiveUnits(activeUnits);
            }
            break;
            case EVENT_LIST: {
                ArrayList<Event> events = (ArrayList<Event>) itemTransporter.getSerializableExtra(DATA);
                caseInstance.setEvents(events);
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
