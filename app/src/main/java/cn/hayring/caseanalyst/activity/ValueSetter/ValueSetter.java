package cn.hayring.caseanalyst.activity.ValueSetter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.io.Serializable;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.pojo.Case;

public abstract class ValueSetter extends AppCompatActivity {
    public static final String CHANGED = "changed";
    public static final int ACTIVE_UNIT_LIST = 3;
    public static final int EVENT_LIST = 4;
    public static final String TYPE = "type";
    public static final String DATA = "data";
    public static final String CREATE_OR_NOT = "create_or_not";
    public static final String POSITION = "position";
    protected Intent requestInfo;
    protected LinearLayout rootLayout;
    protected ScrollView sonView;
    protected EditText nameInputer;
    protected EditText infoInputer;

    public static final String MALE = "男";
    public static final String FEMALE = "女";
    public static final String TRUE = "是";
    public static final String FALSE = "否";
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_setter);
        rootLayout = findViewById(R.id.value_setter_root_layout);
        //editTexts = new ArrayList<EditText>();
        requestInfo = getIntent();

        //加载页面
        loadView();





    }

    /***
     * 加载页面
     */
    abstract void loadView();

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
            dataTransporter.putExtra(ValueSetter.DATA, setData());

            //启动新Activity
            startActivityForResult(dataTransporter, 1);
        }

        abstract Class getListViewClass();

        abstract Serializable setData();
    }




}
