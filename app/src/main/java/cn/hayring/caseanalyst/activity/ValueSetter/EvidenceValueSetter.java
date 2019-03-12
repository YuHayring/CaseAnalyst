package cn.hayring.caseanalyst.activity.ValueSetter;

import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ScrollView;

import java.util.regex.Pattern;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.pojo.Evidence;

public class EvidenceValueSetter extends ValueSetter {
    Evidence evidenceInstance;
    EditText countInputer;


    @Override
    void loadView() {
        //加载layout实例
        LayoutInflater inflater = getLayoutInflater();
        sonView = (ScrollView) inflater.inflate(R.layout.evidence_value_setter, null);
        rootLayout.addView(sonView);
        //注册控件,信息显示
        nameInputer = findViewById(R.id.evidence_name_inputer);
        infoInputer = findViewById(R.id.evidence_info_inputer);
        countInputer = findViewById(R.id.evidence_count_inputer);
        saveButton = findViewById(R.id.evidence_save_button);
        if (!requestInfo.getBooleanExtra(CREATE_OR_NOT, true)) {
            evidenceInstance = (Evidence) requestInfo.getSerializableExtra(DATA);
            nameInputer.setText(evidenceInstance.getName());
            infoInputer.setText(evidenceInstance.getInfo());
            Integer count = evidenceInstance.getCount();
            if (count != null) {
                countInputer.setText(count.toString());
            }
        }
        //设置监听器
        saveButton.setOnClickListener(new EvidenceFinishEditListener());
    }


    /***
     * 保存按钮监听器
     */
    class EvidenceFinishEditListener extends FinishEditListener {
        @Override
        void editReaction() {
            if (evidenceInstance == null) {
                evidenceInstance = caseInstance.createEvidence();
            }

            evidenceInstance.setName(nameInputer.getText().toString());
            evidenceInstance.setInfo(infoInputer.getText().toString());
            String countStr = countInputer.getText().toString();
            if (!"".equals(countStr) && isInteger(countStr)) {
                evidenceInstance.setCount(Integer.valueOf(countStr));
            }
            requestInfo.putExtra(DATA, evidenceInstance);
            requestInfo.putExtra(CHANGED, true);
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
