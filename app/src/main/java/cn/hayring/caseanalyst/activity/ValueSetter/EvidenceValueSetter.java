package cn.hayring.caseanalyst.activity.ValueSetter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Pattern;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.ListActivity.RelationshipListActivity;
import cn.hayring.caseanalyst.pojo.Evidence;
import cn.hayring.caseanalyst.pojo.Relationship;

public class EvidenceValueSetter extends ValueSetter {
    Evidence evidenceInstance;
    EditText countInputer;


    /***
     * 人-物关系入口
     */
    TextView manThingRelationshipEnter;


    /***
     * 组织-物关系入口
     */
    TextView orgThingRelationshipEnter;

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
        } else {
            evidenceInstance = caseInstance.createEvidence();
        }
        manThingRelationshipEnter = sonView.findViewById(R.id.man_thing_relationship_text_view);
        orgThingRelationshipEnter = sonView.findViewById(R.id.org_thing_relationship_text_view);



        //设置监听器
        saveButton.setOnClickListener(new EvidenceFinishEditListener());

        View.OnClickListener relationshipEnterListener = new EditRelationshipListener();
        orgThingRelationshipEnter.setOnClickListener(relationshipEnterListener);
        orgThingRelationshipEnter.setOnClickListener(relationshipEnterListener);
    }


    /***
     * 保存按钮监听器
     */
    class EvidenceFinishEditListener extends FinishEditListener {
        @Override
        void editReaction() {
            /*if (evidenceInstance == null) {
                evidenceInstance = caseInstance.createEvidence();
            }*/

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
     * 关系编辑入口监听器
     */
    class EditRelationshipListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent request = new Intent(EvidenceValueSetter.this, RelationshipListActivity.class);
            if (view.getId() == R.id.org_thing_relationship_text_view) {
                request.putExtra(ValueSetter.DATA, evidenceInstance.getOrgThingRelationships());
                request.putExtra(ValueSetter.RELATIONSHIP_TYPE, Relationship.ORG_EVIDENCE);
            } else if (view.getId() == R.id.man_thing_relationship_text_view) {
                request.putExtra(ValueSetter.DATA, evidenceInstance.getManThingRelationships());
                request.putExtra(ValueSetter.RELATIONSHIP_TYPE, Relationship.MAN_EVIDENCE);
            } else {
                throw new IllegalArgumentException("Error View Id");
            }
            request.putExtra(ValueSetter.CONNECTOR, evidenceInstance);
            startActivityForResult(request, 1);
        }
    }

    /***
     * 编辑完成调用
     * @author Hayring
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent itemTransporter) {
        super.onActivityResult(requestCode, resultCode, itemTransporter);

        //未改变就结束
        if (!itemTransporter.getBooleanExtra(ValueSetter.CHANGED, true)) {
            return;
        }
        ArrayList data = (ArrayList) itemTransporter.getSerializableExtra(ValueSetter.DATA);
        int type = itemTransporter.getIntExtra(ValueSetter.RELATIONSHIP_TYPE, -1);
        if (type == Relationship.ORG_EVIDENCE) {
            evidenceInstance.setOrgThingRelationships(data);
        } else if (type == Relationship.MAN_EVIDENCE) {
            evidenceInstance.setManThingRelationships(data);
        } else {
            throw new IllegalArgumentException("Error relationship type!");
        }


    }

    /***
     * 返回键不保存，返回未改动
     */
    @Override
    public void finish() {
        evidenceInstance = null;
        requestInfo.putExtra(CHANGED, false);
        setResult(2, requestInfo);
        super.finish();
    }


}
