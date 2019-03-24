package cn.hayring.caseanalyst.activity.ValueSetter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.ListActivity.ItemSelectListActivity;
import cn.hayring.caseanalyst.pojo.Event;
import cn.hayring.caseanalyst.pojo.Evidence;
import cn.hayring.caseanalyst.pojo.Listable;
import cn.hayring.caseanalyst.pojo.Organization;
import cn.hayring.caseanalyst.pojo.Person;
import cn.hayring.caseanalyst.pojo.Relationable;
import cn.hayring.caseanalyst.pojo.Relationship;
import cn.hayring.caseanalyst.utils.Pointer;

public class RelationshipValueSetter<T extends Relationable, E extends Relationable> extends ValueSetter {

    boolean isEConnector;
    T itemT;
    E itemE;

    TextView tTextView;
    TextView eTextView;
    EditText keyInputer;
    EditText infoInputer;
    Relationship<T, E> relationshipInstance;
    Listable connector;

    int relationshipType;


    @Override
    void loadView() {
        //加载layout实例
        LayoutInflater inflater = getLayoutInflater();
        sonView = (ScrollView) inflater.inflate(R.layout.relationship_value_setter, null);
        rootLayout.addView(sonView);


        relationshipType = requestInfo.getIntExtra(ValueSetter.RELATIONSHIP_TYPE, -1);

        //注册控件,信息显示
        tTextView = findViewById(R.id.item_t_text_view);
        eTextView = findViewById(R.id.item_e_text_view);
        keyInputer = findViewById(R.id.relationship_key_inputer);
        infoInputer = findViewById(R.id.relationship_info_inputer);
        saveButton = findViewById(R.id.relationship_save_button);

        ChangeListableListener changeListableListener = new ChangeListableListener();
        tTextView.setOnClickListener(changeListableListener);
        eTextView.setOnClickListener(changeListableListener);
        saveButton.setOnClickListener(new RelationshipFinishEditListener());

        //入口元素
        //connector = (Listable) requestInfo.getSerializableExtra(ValueSetter.CONNECTOR);
        connector = Pointer.getConnector();

        if (!requestInfo.getBooleanExtra(CREATE_OR_NOT, true)) {
            //relationshipInstance = (Relationship) requestInfo.getSerializableExtra(DATA);
            relationshipInstance = (Relationship) Pointer.getPoint();

            tTextView.setText(relationshipInstance.getItemT().getName());
            eTextView.setText(relationshipInstance.getItemE().getName());
            keyInputer.setText(relationshipInstance.getKey());
            String info = relationshipInstance.getInfo();
            if (info != null) {
                infoInputer.setText(info);
            }

        } else {
            relationshipInstance = Relationship.createRelationship(relationshipType);
            if (connector.getClass().equals(Person.class)) {
                isEConnector = false;
                itemT = (T) connector;
                tTextView.setText(connector.getName());
            } else if (connector.getClass().equals(Evidence.class) || connector.getClass().equals(Event.class)) {
                isEConnector = true;
                itemE = (E) connector;
                eTextView.setText(connector.getName());
            } else {
                isEConnector = true;
                itemE = (E) connector;
                eTextView.setText(connector.getName());
            }
        }


    }


    /***
     * 保存按钮监听器
     */
    class RelationshipFinishEditListener extends FinishEditListener {
        @Override
        void editReaction() {
            relationshipInstance.setItemE(itemE);
            relationshipInstance.setItemT(itemT);
            relationshipInstance.setKey(keyInputer.getText().toString());
            relationshipInstance.setInfo(infoInputer.getText().toString());

            if (isEConnector) {
                itemT.regRelationship(relationshipInstance);
            } else {
                itemE.regRelationship(relationshipInstance);
            }


            //requestInfo.putExtra(DATA, relationshipInstance);
            if (requestInfo.getBooleanExtra(CREATE_OR_NOT, false)) {
                Pointer.setPoint(relationshipInstance);
            }
            requestInfo.putExtra(CHANGED, true);
        }
    }


    /***
     * 更换元素监听器
     */
    class ChangeListableListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            boolean isE = view.equals(eTextView);
            Class clazz;


            switch (relationshipType) {
                case Relationship.MAN_EVENT: {
                    clazz = isE ? Event.class : Person.class;
                }
                break;
                case Relationship.MAN_MAN: {
                    clazz = Person.class;
                }
                break;
                case Relationship.MAN_EVIDENCE: {
                    clazz = isE ? Evidence.class : Person.class;
                }
                break;
                case Relationship.MAN_ORG: {
                    clazz = isE ? Organization.class : Person.class;
                }
                break;
                case Relationship.ORG_EVENT: {
                    clazz = isE ? Event.class : Organization.class;
                }
                break;
                case Relationship.ORG_EVIDENCE: {
                    clazz = isE ? Evidence.class : Organization.class;
                }
                break;
                default:
                    throw new IllegalArgumentException("Error relationshipType");
            }


            ArrayList dataSrc = caseInstance.getListableList(clazz);
            Intent request = new Intent(RelationshipValueSetter.this, ItemSelectListActivity.class);
            //request.putExtra(ValueSetter.DATA, dataSrc);
            Pointer.setPoint(dataSrc);
            request.putExtra(ValueSetter.IS_E, isE);
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


        if (itemTransporter.getBooleanExtra(ValueSetter.IS_E, true)) {
            //itemE = (E) itemTransporter.getSerializableExtra(ValueSetter.DATA);
            itemE = (E) Pointer.getPoint();
            eTextView.setText(itemE.getName());
        } else {
            //itemT = (T) itemTransporter.getSerializableExtra(ValueSetter.DATA);
            itemT = (T) Pointer.getPoint();
            tTextView.setText(itemT.getName());
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
