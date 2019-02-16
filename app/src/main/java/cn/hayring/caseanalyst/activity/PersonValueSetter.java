package cn.hayring.caseanalyst.activity;

import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ScrollView;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.ValueSetter;
import cn.hayring.caseanalyst.pojo.Case;
import cn.hayring.caseanalyst.pojo.Person;

public class PersonValueSetter extends ValueSetter {
    public Person personInstance;

    void loadView() {
        LayoutInflater inflater = getLayoutInflater();
        sonView = (ScrollView) inflater.inflate(R.layout.person_value_setter, null);
        rootLayout.addView(sonView);
        EditText editText0 = findViewById(R.id.person_name_inputer);
        EditText editText1 = findViewById(R.id.person_info_inputer);
        editTexts.add(editText0);
        editTexts.add(editText1);
        save = findViewById(R.id.person_save_button);
        save.setOnClickListener(new PersonValueSetter.PersonFinishEditListener());
        if (!requestInfo.getBooleanExtra(CREATE_OR_NOT, true)) {
            personInstance = (Person) requestInfo.getSerializableExtra(DATA);
            editText0.setText(personInstance.getName());
            editText1.setText(personInstance.getInfo());
        }
    }


    class PersonFinishEditListener extends FinishEditListener {

        @Override
        void createReaction() {
            //!!!!!!!!!!!!!!Person未注册！！！！！！！！！！！
            EditText editText0 = editTexts.get(0);
            EditText editText1 = editTexts.get(1);
            String name = editText0.getText().toString();
            String info = editText1.getText().toString();

            Person newPerson = new Person(name, null, info);
            requestInfo.putExtra(DATA, newPerson);
        }

        @Override
        void editReaction() {

            Person personInstance = (Person) requestInfo.getSerializableExtra(DATA);
            EditText editText0 = editTexts.get(0);
            EditText editText1 = editTexts.get(1);
            personInstance.setName(editText0.getText().toString());
            personInstance.setInfo(editText1.getText().toString());
            requestInfo.putExtra(DATA, personInstance);
        }
    }


}
