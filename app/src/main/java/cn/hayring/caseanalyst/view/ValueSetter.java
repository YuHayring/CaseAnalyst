package cn.hayring.caseanalyst.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.regex.Pattern;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.domain.Case;
import cn.hayring.caseanalyst.domain.Avatars;
import cn.hayring.caseanalyst.utils.BasisTimesUtils;
import cn.hayring.caseanalyst.utils.Pointer;

public abstract class ValueSetter<T extends Serializable> extends AppCompatActivity {
    public static final String INDEX = "index";
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
    public static final String ID = "id";
    public static final String POSITION = "position";
    public static final String IS_E = "is_e";
    public static final String CASE = "case";
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


    Toolbar toolbar;

    /***
     * 保存按钮
     */
    protected Button saveButton;

    /***
     * 是否为新建元素
     */
    protected boolean isCreate;

    /***
     * 实例
     */
    protected T instance;

    /***
     * 案件实例
     */
    protected static Case caseInstance;


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
        isCreate = requestInfo.getBooleanExtra(CREATE_OR_NOT, true);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /***
     * 保存按钮监听器
     *  TODO        ??子类调用为什么要 public？protected 编译错误
     */
    public class FinishEditListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            save();
            //设置结果
            setResult(2, requestInfo);
            //要注意
            ValueSetter.super.finish();
        }

    }

    /***
     * 保存
     */
    @CallSuper
    protected void save() {
        writeInstance();
        requestInfo.putExtra(CHANGED, true);
        if (isCreate) {
            Pointer.setPoint(instance);
        }
    }

    /***
     * 删除
     */
    protected void onDestory() {

    }

    /***
     * 写入内存
     */
    protected abstract void writeInstance();


    /***
     * ListView入口监听器
     *      TODO        ??子类调用为什么要 public？protected 编译错误
     */
    protected abstract class ListEnterListener implements View.OnClickListener {

        private Context packageContext;

        public ListEnterListener(Context packageContext) {
            this.packageContext = packageContext;
        }


        @Override
        public void onClick(View view) {
            Intent dataTransporter = new Intent(packageContext, getListViewClass());
            Pointer.setPoint(getList());
            //启动新Activity
            startActivity(dataTransporter);
        }

        protected abstract Class getListViewClass();

        protected abstract Serializable getList();
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


    /***
     * 注册时间选择器
     * @param dateLabel
     * @param timeLabel
     * @param context
     * @param time
     */
    protected void setOnClickListenerForTimeSetter(TextView dateLabel, TextView timeLabel, Context context, Calendar time) {
        if (caseInstance.isShortTimeCase()) {
            dateLabel.setOnClickListener(view -> {
                BasisTimesUtils.showDatePickerDialog(context, true, "", 2000, 1, 1,
                        new BasisTimesUtils.OnDatePickerListener() {

                            @Override
                            public void onConfirm(int year, int month, int dayOfMonth) {
                                if (time.get(Calendar.YEAR) == 1970) {
                                    time.setTimeInMillis(946656000000l);
                                }
                                time.set(Calendar.DATE, dayOfMonth);
                                dateLabel.setText("第" + dayOfMonth + "天");
                            }

                            @Override
                            public void onCancel() {
                            }
                        }).setOnlyDay();
            });
        } else {
            dateLabel.setOnClickListener(view -> {
                BasisTimesUtils.showDatePickerDialog(context, true, "", 2000, 1, 1,
                        new BasisTimesUtils.OnDatePickerListener() {

                            @Override
                            public void onConfirm(int year, int month, int dayOfMonth) {
                                time.set(Calendar.YEAR, year);
                                time.set(Calendar.MONTH, month - 1);
                                time.set(Calendar.DATE, dayOfMonth);
                                dateLabel.setText(dateFormatter.format(time.getTime()));
                            }

                            @Override
                            public void onCancel() {
                            }
                        });
            });
        }
        timeLabel.setOnClickListener(view -> {

            BasisTimesUtils.showTimerPickerDialog(context, true, "请选择时间", 00, 00, true, new BasisTimesUtils.OnTimerPickerListener() {
                @Override
                public void onConfirm(int hourOfDay, int minute) {
                    if (time.get(Calendar.YEAR) == 1970) {
                        time.setTimeInMillis(946656000000l);
                    }
                    time.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    time.set(Calendar.MINUTE, minute);
                    timeLabel.setText(timeFormatter.format(time.getTime()));
                }

                @Override
                public void onCancel() {
                }
            });
        });
    }


    @Override
    public void onPause() {
        writeInstance();
        super.onPause();
    }



    /***
     * 返回键判断是否需要保存
     */
    @Override
    public void finish() {
        if (isCreate) {
            onDestory();
            requestInfo.putExtra(CHANGED, false);
        } else {
            save();
        }
        setResult(2, requestInfo);
        super.finish();
    }

    public static void setCaseInstance(Case caseInstance) {
        ValueSetter.caseInstance = caseInstance;
    }

    public static Bitmap loadHeadImage(Avatars instance, ImageView headImage, Context context) {
        if (instance.getImageIndex() != null) {
            try {
                FileInputStream headIS = context.openFileInput(instance.getImageIndex() + ".jpg");
                Bitmap image = BitmapFactory.decodeStream(headIS);
                headImage.setImageBitmap(image);
                return image;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

}
