package cn.hayring.caseanalyst.activity.ValueSetter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.ListActivity.RelationshipListActivity;
import cn.hayring.caseanalyst.pojo.Event;
import cn.hayring.caseanalyst.pojo.Relationship;

public class EventValueSetter extends ValueSetter {
    Event eventInstance;

    /***
     * 组织-事关系入口
     */
    TextView orgEventRelationshipEnter;

    /***
     * 人-事关系入口
     */
    TextView manEventRelationshipEnter;



    /***
     * 加载页面
     */
    @Override
    public void loadView() {
        //加载layout实例
        LayoutInflater inflater = getLayoutInflater();
        sonView = (ScrollView) inflater.inflate(R.layout.event_value_setter, null);
        rootLayout.addView(sonView);

        //注册控件,信息显示
        nameInputer = findViewById(R.id.event_name_inputer);
        infoInputer = findViewById(R.id.event_info_inputer);
        saveButton = findViewById(R.id.event_save_button);
        if (!requestInfo.getBooleanExtra(CREATE_OR_NOT, true)) {
            eventInstance = (Event) requestInfo.getSerializableExtra(DATA);
            nameInputer.setText(eventInstance.getName());
            infoInputer.setText(eventInstance.getInfo());
        } else {
            eventInstance = caseInstance.createEvent();
        }

        orgEventRelationshipEnter = sonView.findViewById(R.id.org_event_relationship_text_view);
        manEventRelationshipEnter = sonView.findViewById(R.id.man_event_relationship_text_view);

        //设置监听器
        saveButton.setOnClickListener(new EventFinishEditListener());

        View.OnClickListener relationshipEnterListener = new EditRelationshipListener();
        orgEventRelationshipEnter.setOnClickListener(relationshipEnterListener);
        manEventRelationshipEnter.setOnClickListener(relationshipEnterListener);


    }

    /***
     * 保存按钮监听器
     */
    class EventFinishEditListener extends FinishEditListener {
        @Override
        void editReaction() {
            /*if (eventInstance == null) {
                eventInstance = caseInstance.createEvent();
            }*/
            eventInstance.setName(nameInputer.getText().toString());
            eventInstance.setInfo(infoInputer.getText().toString());
            requestInfo.putExtra(DATA, eventInstance);
            requestInfo.putExtra(CHANGED, true);
        }
    }


    /***
     * 关系编辑入口监听器
     */
    class EditRelationshipListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent request = new Intent(EventValueSetter.this, RelationshipListActivity.class);
            if (view.getId() == R.id.org_event_relationship_text_view) {
                request.putExtra(ValueSetter.DATA, eventInstance.getOrgEventRelationships());
                request.putExtra(ValueSetter.RELATIONSHIP_TYPE, Relationship.ORG_EVENT);
            } else if (view.getId() == R.id.man_event_relationship_text_view) {
                request.putExtra(ValueSetter.DATA, eventInstance.getManEventRelationships());
                request.putExtra(ValueSetter.RELATIONSHIP_TYPE, Relationship.MAN_EVENT);
            } else {
                throw new IllegalArgumentException("Error View Id");
            }
            request.putExtra(ValueSetter.CONNECTOR, eventInstance);
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
        if (type == Relationship.ORG_EVENT) {
            eventInstance.setOrgEventRelationships(data);
        } else if (type == Relationship.MAN_EVENT) {
            eventInstance.setManEventRelationships(data);
        } else {
            throw new IllegalArgumentException("Error relationship type!");
        }


    }

    /***
     * 返回键不保存，返回未改动
     */
    @Override
    public void finish() {
        eventInstance = null;
        requestInfo.putExtra(CHANGED, false);
        setResult(2, requestInfo);
        super.finish();
    }


}
