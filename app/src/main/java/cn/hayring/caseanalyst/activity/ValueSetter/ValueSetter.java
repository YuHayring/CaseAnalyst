package cn.hayring.caseanalyst.activity.ValueSetter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.regex.Pattern;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.pojo.Case;
import cn.hayring.caseanalyst.utils.Pointer;

public class ValueSetter extends AppCompatActivity {
    public static final String CONNECTOR = "connector";
    public static final String CHANGED = "changed";
    public static final int PERSON_LIST = 2;
    public static final int ORG_LIST = 3;
    public static final int EVENT_LIST = 4;
    public static final int EVIDENCE_LIST = 5;
    public static final int RELATIONSHIP_LIST = 6;
    public static final int MAN_MAN_RELATIONSHIP_LIST = 7;
    public static final String RELATIONSHIP_TYPE = "relationship_type";
    public static final String TYPE = "type";
    public static final String DATA = "data";
    public static final String CREATE_OR_NOT = "create_or_not";
    public static final String POSITION = "position";
    public static final String IS_E = "is_e";
    protected Intent requestInfo;
    protected LinearLayout rootLayout;
    protected ScrollView sonView;
    protected EditText nameInputer;
    protected EditText infoInputer;

    public static final String MALE = "男";
    public static final String FEMALE = "女";
    public static final String TRUE = "是";
    public static final String FALSE = "否";

    public static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");


    public static Random random = new Random();


    //废弃
    //protected ArrayList<EditText> editTexts;

    /***
     * 保存按钮
     */
    protected Button saveButton;

    /***
     * 案件实例
     */
    public static Case caseInstance;


    ////-----------------------------debug code
    //public static ArrayList<Case> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_setter);
        //加载页面
        initView();
    }

    /***
     * 加载页面
     */
    @CallSuper
    protected void initView() {
        rootLayout = findViewById(R.id.value_setter_root_layout);
        //editTexts = new ArrayList<EditText>();
        requestInfo = getIntent();
    }

    /***
     * 保存按钮监听器
     */
    abstract class FinishEditListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            editReaction();
            //设置结果
            setResult(2, requestInfo);
            //要注意
            ValueSetter.super.finish();
        }


        /***
         * 编辑元素完成行为
         */
        abstract void editReaction();


    }

    /***
     * ListView入口监听器
     */
    abstract class ListEnterListener implements View.OnClickListener {

        private Context packageContext;

        public ListEnterListener(Context packageContext) {
            this.packageContext = packageContext;
        }


        @Override
        public void onClick(View view) {
            Intent dataTransporter = new Intent(packageContext, getListViewClass());
            //dataTransporter.putExtra(ValueSetter.DATA, getList());
            Pointer.setPoint(getList());
            //启动新Activity
            startActivity(dataTransporter);
        }

        abstract Class getListViewClass();

        abstract Serializable getList();
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /***
     * 关系列表编辑完成调用
     * @author Hayring
     *//*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent itemTransporter) {
        super.onActivityResult(requestCode, resultCode, itemTransporter);
        int type = itemTransporter.getIntExtra(TYPE, -1);
        if(type == RELATIONSHIP_LIST) {
            ArrayList<Relationship> relationships = (ArrayList<Relationship>) itemTransporter.getSerializableExtra(DATA);

        }
        throw new IllegalArgumentException("Error type!");


    }*/


}
