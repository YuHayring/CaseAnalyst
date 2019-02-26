package cn.hayring.caseanalyst.activity.ValueSetter;

import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.pojo.Person;

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

        //设置监听器
        saveButton.setOnClickListener(new PersonValueSetter.PersonFinishEditListener());

        //设置属性显示
        if (!requestInfo.getBooleanExtra(CREATE_OR_NOT, true)) {
            personInstance = (Person) requestInfo.getSerializableExtra(DATA);
            //基本信息设置
            nameInputer.setText(personInstance.getName());
            infoInputer.setText(personInstance.getInfo());
            nickNameInputer.setText(personInstance.getNickName());
            ageInputer.setText(personInstance.getAge().toString());

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


        } else {
            personInstance = caseInstance.createPerson();
        }
    }


    class PersonFinishEditListener extends FinishEditListener {

        @Override
        void editReaction() {
            //获取输入框数据
            String name = nameInputer.getText().toString();
            String info = infoInputer.getText().toString();
            String nickName = nickNameInputer.getText().toString();
            Integer age = Integer.valueOf(ageInputer.getText().toString());
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
            personInstance.setAge(age);
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
