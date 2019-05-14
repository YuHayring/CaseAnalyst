package cn.hayring.caseanalyst.activity.ValueSetter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.ListActivity.ItemSelectListActivity;
import cn.hayring.caseanalyst.bean.Event;
import cn.hayring.caseanalyst.bean.Evidence;
import cn.hayring.caseanalyst.bean.HaveHead;
import cn.hayring.caseanalyst.bean.Listable;
import cn.hayring.caseanalyst.bean.Organization;
import cn.hayring.caseanalyst.bean.Person;
import cn.hayring.caseanalyst.bean.Relationable;
import cn.hayring.caseanalyst.bean.Relationship;
import cn.hayring.caseanalyst.utils.Pointer;

public class RelationshipValueSetter<T extends Relationable, E extends Relationable> extends ValueSetter<Relationship<T, E>> {

    /***
     * E实例是否为入口实例
     */
    boolean isEConnector;

    /***
     * 元素T,类签名靠前的那个类
     */
    T itemT;

    /***
     * 元素E，类签名靠后的那个类
     */
    E itemE;

    //元素名称显示器
    TextView tTextView;
    TextView eTextView;

    /***
     * 关键字输入框
     */
    EditText keyInputer;

    /***
     * 信息输入框
     */
    EditText infoInputer;

    /***
     * T头像
     */
    ImageView imageViewT;

    /***
     * E头像
     */
    ImageView imageViewE;

    /***
     * 入口元素
     */
    Listable connector;

    /***
     * 关系类型，ValueSetter静态常量
     */
    int relationshipType;


    @Override
    protected void initView() {
        super.initView();
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
        imageViewE = findViewById(R.id.imageViewE);
        imageViewT = findViewById(R.id.imageViewT);

        ChangeListableListener changeListableListener = new ChangeListableListener();
        tTextView.setOnClickListener(changeListableListener);
        eTextView.setOnClickListener(changeListableListener);
        saveButton.setOnClickListener(new FinishEditListener());

        //入口元素
        //connector = (Listable) requestInfo.getSerializableExtra(ValueSetter.CONNECTOR);
        connector = Pointer.getConnector();


        if (!isCreate) {
            saveButton.setEnabled(false);
            saveButton.setVisibility(View.GONE);
            //instance = (Relationship) requestInfo.getSerializableExtra(DATA);
            instance = (Relationship) Pointer.getPoint();

            isEConnector = instance.getItemE().equals(connector);
            itemT = instance.getItemT();
            itemE = instance.getItemE();

            tTextView.setText(instance.getItemT().getName());
            eTextView.setText(instance.getItemE().getName());
            keyInputer.setText(instance.getKey());
            String info = instance.getInfo();
            if (info != null) {
                infoInputer.setText(info);
            }


            //Event只会在E的位置出现，判断是否有头像并加载
            if (!(itemE instanceof Event)) {
                loadHeadImage((HaveHead) itemE, imageViewE, this);
            } else if (itemE instanceof Event) {
                imageViewE.setEnabled(false);
            }

            //判断是否有头像并加载
            loadHeadImage((HaveHead) itemT, imageViewT, this);


        } else {
            instance = Relationship.createRelationship(relationshipType);
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

            if (!(connector instanceof Event)) {
                if (isEConnector) {
                    loadHeadImage((HaveHead) itemE, imageViewE, this);
                } else {
                    loadHeadImage((HaveHead) itemT, imageViewT, this);
                }
            }
        }


    }


    @Override
    protected void writeInstance() {
        instance.setItemE(itemE);
        instance.setItemT(itemT);
        instance.setKey(keyInputer.getText().toString());
        instance.setInfo(infoInputer.getText().toString());
    }

    @Override
    protected void save() {
        writeInstance();
        if (isEConnector) {
            itemT.regRelationship(instance);
        } else {
            itemE.regRelationship(instance);
        }
        super.save();
    }


    /***
     * 更换元素监听器
     */
    class ChangeListableListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            boolean isE = view.equals(eTextView);
            Class clazz;


            //确定申请选择的数据类型
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
                    throw new IllegalArgumentException("Error relationshipType or null pointer object");
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


}
