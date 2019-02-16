package cn.hayring.caseanalyst.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.pojo.ActiveUnit;
import cn.hayring.caseanalyst.pojo.Case;
import cn.hayring.caseanalyst.pojo.Listable;

public class CaseValueSetter extends ValueSetter {
    private Case caseInstance;

    TextView activeUnitsEnter;

    /***
     * 加载页面
     */
    @Override
    public void loadView() {
        LayoutInflater inflater = getLayoutInflater();
        sonView = (ScrollView) inflater.inflate(R.layout.case_value_setter, null);
        rootLayout.addView(sonView);
        EditText editText0 = findViewById(R.id.case_name_inputer);
        EditText editText1 = findViewById(R.id.case_info_inputer);
        editTexts.add(editText0);
        editTexts.add(editText1);
        save = findViewById(R.id.case_save_button);
        save.setOnClickListener(new CaseFinishEditListener());
        if (!requestInfo.getBooleanExtra(CREATE_OR_NOT, true)) {
            caseInstance = (Case) requestInfo.getSerializableExtra(DATA);
            editText0.setText(caseInstance.getName());
            editText1.setText(caseInstance.getInfo());
        }
        activeUnitsEnter = findViewById(R.id.active_unit_list_enter);
        activeUnitsEnter.setOnClickListener(new ActiveUnitsEnterListener(this));



    }

    class CaseFinishEditListener extends FinishEditListener {

        @Override
        void createReaction() {
            caseInstance = new Case();
            EditText editText0 = editTexts.get(0);
            EditText editText1 = editTexts.get(1);
            caseInstance.setName(editText0.getText().toString());
            caseInstance.setInfo(editText1.getText().toString());
            requestInfo.putExtra(DATA, caseInstance);
        }

        @Override
        void editReaction() {
            Case caseInstance = (Case) requestInfo.getSerializableExtra(DATA);
            EditText editText0 = editTexts.get(0);
            EditText editText1 = editTexts.get(1);
            caseInstance.setName(editText0.getText().toString());
            caseInstance.setInfo(editText1.getText().toString());
            requestInfo.putExtra(DATA, caseInstance);
        }
    }

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
     * 编辑完成调用
     * @author Hayring
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent itemTransporter) {
        super.onActivityResult(requestCode, resultCode, itemTransporter);
        int type = itemTransporter.getIntExtra(TYPE, -1);
        switch (type) {
            case ACTIVE_UNIT_LIST: {
                ArrayList<ActiveUnit> activeUnits = (ArrayList<ActiveUnit>) itemTransporter.getSerializableExtra(DATA);
                caseInstance.setActiveUnits(activeUnits);
            }
            break;
            default:
                throw new IllegalArgumentException("Error type!");
        }


    }


}
