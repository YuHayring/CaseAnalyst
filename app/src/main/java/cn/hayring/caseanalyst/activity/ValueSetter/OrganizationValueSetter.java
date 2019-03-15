package cn.hayring.caseanalyst.activity.ValueSetter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.ListActivity.RelationshipListActivity;
import cn.hayring.caseanalyst.pojo.Organization;
import cn.hayring.caseanalyst.pojo.Relationship;

public class OrganizationValueSetter extends ValueSetter {
    Organization orgInstance;


    /***
     * 组织-组织关系入口
     */
    TextView orgOrgRelationshipEnter;

    /***
     * 人-组织关系入口
     */
    TextView manOrgRelationshipEnter;

    /***
     * 组织-事关系入口
     */
    TextView orgEventRelationshipEnter;

    /***
     * 组织-物关系入口
     */
    TextView orgThingRelationshipEnter;

    /***
     * 加载页面
     */
    @Override
    public void loadView() {
        //加载layout实例
        LayoutInflater inflater = getLayoutInflater();
        sonView = (ScrollView) inflater.inflate(R.layout.org_value_setter, null);
        rootLayout.addView(sonView);

        //注册控件,信息显示
        nameInputer = findViewById(R.id.org_name_inputer);
        infoInputer = findViewById(R.id.org_info_inputer);
        saveButton = findViewById(R.id.org_save_button);
        if (!requestInfo.getBooleanExtra(CREATE_OR_NOT, true)) {
            orgInstance = (Organization) requestInfo.getSerializableExtra(DATA);
            nameInputer.setText(orgInstance.getName());
            infoInputer.setText(orgInstance.getInfo());
        } else {
            orgInstance = caseInstance.createOrganization();
        }

        orgEventRelationshipEnter = sonView.findViewById(R.id.org_event_relationship_text_view);
        orgOrgRelationshipEnter = sonView.findViewById(R.id.org_org_relationship_text_view);
        manOrgRelationshipEnter = sonView.findViewById(R.id.man_org_relationship_text_view);
        orgThingRelationshipEnter = sonView.findViewById(R.id.org_thing_relationship_text_view);

        //设置监听器
        saveButton.setOnClickListener(new OrganizationFinishEditListener());

        View.OnClickListener relationshipEnterListener = new EditRelationshipListener();

        orgEventRelationshipEnter.setOnClickListener(relationshipEnterListener);
        orgThingRelationshipEnter.setOnClickListener(relationshipEnterListener);
        orgOrgRelationshipEnter.setOnClickListener(relationshipEnterListener);
        manOrgRelationshipEnter.setOnClickListener(relationshipEnterListener);

    }

    /***
     * 保存按钮监听器
     */
    class OrganizationFinishEditListener extends FinishEditListener {
        @Override
        void editReaction() {
            /*if (orgInstance == null) {
                orgInstance = caseInstance.createOrganization();
            }*/
            orgInstance.setName(nameInputer.getText().toString());
            orgInstance.setInfo(infoInputer.getText().toString());
            requestInfo.putExtra(DATA, orgInstance);
            requestInfo.putExtra(CHANGED, true);
        }
    }


    /***
     * 关系编辑入口监听器
     */
    class EditRelationshipListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent request = new Intent(OrganizationValueSetter.this, RelationshipListActivity.class);
            switch (view.getId()) {
                case R.id.org_event_relationship_text_view: {
                    request.putExtra(ValueSetter.DATA, orgInstance.getOrgEventRelationships());
                    request.putExtra(ValueSetter.RELATIONSHIP_TYPE, Relationship.ORG_EVENT);
                }
                break;
                case R.id.org_org_relationship_text_view: {
                    request.putExtra(ValueSetter.DATA, orgInstance.getOrgOrgRelationships());
                    request.putExtra(ValueSetter.RELATIONSHIP_TYPE, Relationship.ORG_ORG);
                }
                break;
                case R.id.man_org_relationship_text_view: {
                    request.putExtra(ValueSetter.DATA, orgInstance.getManOrgRelationships());
                    request.putExtra(ValueSetter.RELATIONSHIP_TYPE, Relationship.MAN_ORG);
                }
                break;
                case R.id.org_thing_relationship_text_view: {
                    request.putExtra(ValueSetter.DATA, orgInstance.getOrgThingRelationships());
                    request.putExtra(ValueSetter.RELATIONSHIP_TYPE, Relationship.ORG_EVIDENCE);
                }
                break;
                default:
                    throw new IllegalArgumentException("Error View Id");
            }
            request.putExtra(ValueSetter.CONNECTOR, orgInstance);
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
        switch (itemTransporter.getIntExtra(ValueSetter.RELATIONSHIP_TYPE, -1)) {
            case Relationship.ORG_EVENT: {
                orgInstance.setOrgEventRelationships(data);
            }
            break;
            case Relationship.ORG_ORG: {
                orgInstance.setOrgOrgRelationships(data);
            }
            break;
            case Relationship.MAN_ORG: {
                orgInstance.setManOrgRelationships(data);
            }
            break;
            case Relationship.ORG_EVIDENCE: {
                orgInstance.setOrgThingRelationships(data);
            }
            break;
            default:
                throw new IllegalArgumentException("Error relationship type!");
        }


    }




    /***
     * 返回键不保存，返回未改动
     */
    @Override
    public void finish() {
        orgInstance = null;
        requestInfo.putExtra(CHANGED, false);
        setResult(2, requestInfo);
        super.finish();
    }


}
