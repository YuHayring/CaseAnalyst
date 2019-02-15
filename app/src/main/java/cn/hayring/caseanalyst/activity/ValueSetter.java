package cn.hayring.caseanalyst.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

import cn.hayring.caseanalyst.R;

public abstract class ValueSetter extends AppCompatActivity {
    public static final String DATA = "data";
    public static final String CREATE_OR_NOT = "create_or_not";
    public static final String POSITION = "position";
    protected Intent requestInfo;
    protected LinearLayout rootLayout;
    protected ScrollView sonView;


    protected ArrayList<EditText> editTexts;

    protected Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_setter);
        rootLayout = findViewById(R.id.value_setter_root_layout);
        editTexts = new ArrayList<EditText>();
        requestInfo = getIntent();

        //加载页面
        loadView();


/*        switch (requestInfo.getIntExtra(TYPE, -1)) {
            case CASE: {
                //加载页面
                LayoutInflater inflater = getLayoutInflater();
                sonView = (ScrollView) inflater.inflate(R.layout.case_value_setter, null);
                rootLayout.addView(sonView);
                editText1 = findViewById(R.id.case_name_inputer);
                editText2 = findViewById(R.id.case_info_inputer);
                save = findViewById(R.id.case_save_button);
                save.setOnClickListener(new FinishEditListener());
                if (!requestInfo.getBooleanExtra(CREATE_OR_NOT, true)) {
                    Case caseInstance = (Case) requestInfo.getSerializableExtra(DATA);
                    editText1.setText(caseInstance.getName());
                    editText2.setText(caseInstance.getInfo());
                }



            }
            break;
            default:
                throw new IllegalArgumentException("There is no macthing type");
        }*/


    }

    /***
     * 加载页面
     */
    abstract void loadView();

    abstract class FinishEditListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (requestInfo.getBooleanExtra(CREATE_OR_NOT, false)) {
                createReaction();
            } else if (!requestInfo.getBooleanExtra(CREATE_OR_NOT, true)) {
                editReaction();
            }
            setResult(2, requestInfo);
            finish();
        }

        /***
         * 创建元素行为
         */
        abstract void createReaction();

        /***
         * 编辑元素行为
         */
        abstract void editReaction();


    }


}
