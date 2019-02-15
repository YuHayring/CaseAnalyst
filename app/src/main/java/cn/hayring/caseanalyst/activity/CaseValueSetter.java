package cn.hayring.caseanalyst.activity;

import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ScrollView;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.pojo.Case;

public class CaseValueSetter extends ValueSetter {

    /***
     * 加载页面
     */
    @Override
    void loadView() {
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
            Case caseInstance = (Case) requestInfo.getSerializableExtra(DATA);
            editText0.setText(caseInstance.getName());
            editText1.setText(caseInstance.getInfo());
        }


    }

    class CaseFinishEditListener extends FinishEditListener {

        @Override
        void createReaction() {
            Case newCase = new Case();
            EditText editText0 = editTexts.get(0);
            EditText editText1 = editTexts.get(1);
            newCase.setName(editText0.getText().toString());
            newCase.setInfo(editText1.getText().toString());
            requestInfo.putExtra(DATA, newCase);
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
}
