package cn.hayring.caseanalyst.activity.ValueSetter;

import android.view.LayoutInflater;
import android.widget.ScrollView;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.pojo.Event;

public class EventValueSetter extends ValueSetter {
    Event eventInstance;


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
        }

        //设置监听器
        saveButton.setOnClickListener(new EventFinishEditListener());


    }

    /***
     * 保存按钮监听器
     */
    class EventFinishEditListener extends FinishEditListener {
        @Override
        void editReaction() {
            if (eventInstance == null) {
                eventInstance = caseInstance.createEvent();
            }
            eventInstance.setName(nameInputer.getText().toString());
            eventInstance.setInfo(infoInputer.getText().toString());
            requestInfo.putExtra(DATA, eventInstance);
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
