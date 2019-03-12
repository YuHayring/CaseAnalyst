package cn.hayring.caseanalyst.activity.ValueSetter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.ListActivity.RelationshipListActivity;
import cn.hayring.caseanalyst.pojo.Person;
import cn.hayring.caseanalyst.pojo.Relationship;

public class PersonValueSetter extends ValueSetter {
    /***
     * 人实例
     */
    public Person personInstance;

    /***
     * 性别选择器
     */
    Spinner genderSwitcher;

    /***
     * 血型选择器
     */
    Spinner bloodTypeSwitcher;

    /***
     * 嫌犯属性选择器
     */
    Spinner suspectSwitcher;

    /***
     * 昵称输入框
     */
    EditText nickNameInputer;

    /***
     * 年龄输入框
     */
    EditText ageInputer;

    /***
     * 人际关系入口
     */
    TextView manManRelationshipEnter;

    /***
     * 人-组织关系入口
     */
    TextView manOrgRelationshipEnter;

    /***
     * 人-事关系入口
     */
    TextView manEventRelationshipEnter;

    /***
     * 人-物关系入口
     */
    TextView manThingRelationshipEnter;

    /***
     * View加载
     */
    void loadView() {
        //加载layout实例
        LayoutInflater inflater = getLayoutInflater();
        sonView = (ScrollView) inflater.inflate(R.layout.person_value_setter, null);
        rootLayout.addView(sonView);

        //注册控件
        nameInputer = findViewById(R.id.person_name_inputer);
        infoInputer = findViewById(R.id.person_info_inputer);
        nickNameInputer = findViewById(R.id.person_nick_name_inputer);
        ageInputer = findViewById(R.id.person_age_inputer);
        genderSwitcher = findViewById(R.id.gender_switcher);
        bloodTypeSwitcher = findViewById(R.id.blood_type_switcher);
        suspectSwitcher = findViewById(R.id.suspect_switcher);
        saveButton = findViewById(R.id.person_save_button);

        manEventRelationshipEnter = findViewById(R.id.man_event_relationship_text_view);
        manManRelationshipEnter = findViewById(R.id.man_man_relationship_text_view);
        manOrgRelationshipEnter = findViewById(R.id.man_org_relationship_text_view);
        manThingRelationshipEnter = findViewById(R.id.man_thing_relationship_text_view);

        View.OnClickListener relationshipEnterListener = new EditRelationshipListener();

        manEventRelationshipEnter.setOnClickListener(relationshipEnterListener);
        manThingRelationshipEnter.setOnClickListener(relationshipEnterListener);
        manManRelationshipEnter.setOnClickListener(relationshipEnterListener);
        manOrgRelationshipEnter.setOnClickListener(relationshipEnterListener);



        //设置监听器
        saveButton.setOnClickListener(new PersonFinishEditListener());

        //设置属性显示
        if (!requestInfo.getBooleanExtra(CREATE_OR_NOT, true)) {
            personInstance = (Person) requestInfo.getSerializableExtra(DATA);
            //基本信息设置
            nameInputer.setText(personInstance.getName());
            infoInputer.setText(personInstance.getInfo());
            nickNameInputer.setText(personInstance.getNickName());
            Integer age = personInstance.getAge();
            if (age != null) {
                ageInputer.setText(age.toString());
            }


            //设置性别
            if (null == personInstance.getGender()) {
                genderSwitcher.setSelection(2);
            } else if (personInstance.getGender()/*==Person.MALE*/) {
                genderSwitcher.setSelection(0);
            } else {
                genderSwitcher.setSelection(1);
            }

            //设置是否为嫌犯
            if (null == personInstance.getSuspect()) {
                suspectSwitcher.setSelection(2);
            } else if (personInstance.getGender()) {
                suspectSwitcher.setSelection(0);
            } else {
                suspectSwitcher.setSelection(1);
            }
            String bloodType = personInstance.getBloodType();
            if (bloodType != null) {
                switch (bloodType) {
                    case Person.A: {
                        bloodTypeSwitcher.setSelection(0);
                    }
                    break;
                    case Person.AB: {
                        bloodTypeSwitcher.setSelection(2);
                    }
                    break;
                    case Person.B: {
                        bloodTypeSwitcher.setSelection(1);
                    }
                    break;
                    case Person.O: {
                        bloodTypeSwitcher.setSelection(3);
                    }
                    break;
                    case Person.RHAB: {
                        bloodTypeSwitcher.setSelection(4);
                    }
                    break;
                    default:
                        bloodTypeSwitcher.setSelection(5);
                }
            } else {
                bloodTypeSwitcher.setSelection(5);
            }


        }
    }


    class PersonFinishEditListener extends FinishEditListener {

        @Override
        void editReaction() {
            if (personInstance == null) {
                personInstance = caseInstance.createPerson();
            }
            //获取输入框数据
            String name = nameInputer.getText().toString();
            String info = infoInputer.getText().toString();
            String nickName = nickNameInputer.getText().toString();
            String genderStr = genderSwitcher.getSelectedItem().toString();
            Boolean gender;
            if (genderStr.equals(MALE)) {
                gender = Person.MALE;
            } else if (genderStr.equals(FEMALE)) {
                gender = Person.FEMALE;
            } else {
                gender = null;
            }

            //嫌犯属性
            String suspectStr = suspectSwitcher.getSelectedItem().toString();
            Boolean suspect;
            if (suspectStr.equals(TRUE)) {
                suspect = true;
            } else if (suspectStr.equals(FALSE)) {
                suspect = false;
            } else {
                suspect = null;
            }


            //设置已输入的数据
            personInstance.setName(name);
            personInstance.setInfo(info);
            personInstance.setSuspect(suspect);
            personInstance.setGender(gender);
            personInstance.setNickName(nickName);


            String ageStr = ageInputer.getText().toString();
            if (!"".equals(ageStr) && isInteger(ageStr)) {
                personInstance.setAge(Integer.valueOf(ageStr));
            }




            switch (bloodTypeSwitcher.getSelectedItem().toString()) {
                case Person.A:
                case Person.B:
                case Person.AB:
                case Person.O:
                case Person.RHAB:
                    personInstance.setBloodType(bloodTypeSwitcher.getSelectedItem().toString());
                default:
            }
            //装载数据
            requestInfo.putExtra(DATA, personInstance);
            requestInfo.putExtra(CHANGED, true);
        }


    }


    class EditRelationshipListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent request = new Intent(PersonValueSetter.this, RelationshipListActivity.class);
            switch (view.getId()) {
                case R.id.man_event_relationship_text_view: {
                    request.putExtra(ValueSetter.DATA, personInstance.getManEventRelationships());
                    request.putExtra(ValueSetter.RELATIONSHIP_TYPE, Relationship.MAN_EVENT);
                }
                break;
                case R.id.man_man_relationship_text_view: {
                    request.putExtra(ValueSetter.DATA, personInstance.getManManRelationships());
                    request.putExtra(ValueSetter.RELATIONSHIP_TYPE, Relationship.MAN_MAN);
                }
                break;
                case R.id.man_org_relationship_text_view: {
                    request.putExtra(ValueSetter.DATA, personInstance.getManOrgRelationships());
                    request.putExtra(ValueSetter.RELATIONSHIP_TYPE, Relationship.MAN_ORG);
                }
                break;
                case R.id.man_thing_relationship_text_view: {
                    request.putExtra(ValueSetter.DATA, personInstance.getManThingRelationships());
                    request.putExtra(ValueSetter.RELATIONSHIP_TYPE, Relationship.MAN_EVIDENCE);
                }
                break;
                default:
                    throw new IllegalArgumentException("Error View Id");
            }
            request.putExtra(ValueSetter.CONNECTOR, personInstance);
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
            case Relationship.MAN_EVENT: {
                personInstance.setManEventRelationships(data);
            }
            break;
            case Relationship.MAN_MAN: {
                personInstance.setManManRelationships(data);
            }
            break;
            case Relationship.MAN_ORG: {
                personInstance.setManOrgRelationships(data);
            }
            break;
            case Relationship.MAN_EVIDENCE: {
                personInstance.setManThingRelationships(data);
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
        requestInfo.putExtra(CHANGED, false);
        setResult(2, requestInfo);
        super.finish();
    }


}
