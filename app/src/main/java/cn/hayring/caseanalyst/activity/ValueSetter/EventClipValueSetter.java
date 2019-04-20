package cn.hayring.caseanalyst.activity.ValueSetter;

import android.view.LayoutInflater;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.pojo.EventClip;
import cn.hayring.caseanalyst.utils.Pointer;

public class EventClipValueSetter extends ValueSetter {

    TextView happenDate;

    TextView happenTime;

    EventClip eventClipInstance;

    Calendar calendar;

    /***
     * 加载页面
     */
    @Override
    protected void initView() {
        super.initView();
        //加载layout实例
        LayoutInflater inflater = getLayoutInflater();
        sonView = (ScrollView) inflater.inflate(R.layout.event_clip_value_setter, null);
        rootLayout.addView(sonView);

        //注册控件,信息显示
        infoInputer = findViewById(R.id.event_clip_info_inputer);
        saveButton = findViewById(R.id.event_clip_save_button);
        happenDate = findViewById(R.id.happen_date);
        happenTime = findViewById(R.id.happen_time);

        if (!requestInfo.getBooleanExtra(CREATE_OR_NOT, true)) {
            //eventInstance = (Event) requestInfo.getSerializableExtra(DATA);
            eventClipInstance = (EventClip) Pointer.getPoint();
            infoInputer.setText(eventClipInstance.getInfo());
            happenDate.setText(dateFormatter.format(eventClipInstance.getTimePoint().getTime()));
            happenTime.setText(timeFormatter.format(eventClipInstance.getTimePoint().getTime()));
        } else {
            eventClipInstance = new EventClip();
        }

        setOnClickListenerForTimeSetter(happenDate, happenTime, this, eventClipInstance.getTimePoint());

        //设置监听器
        saveButton.setOnClickListener(new EventClipFinishEditListener());


    }


    /***
     * 保存按钮监听器
     */
    class EventClipFinishEditListener extends FinishEditListener {
        @Override
        void editReaction() {
            if (eventClipInstance.getTimePoint().get(Calendar.YEAR) == 1970) {
                Toast.makeText(EventClipValueSetter.this, "时间未设置", Toast.LENGTH_SHORT);
                return;
            }
            eventClipInstance.setInfo(infoInputer.getText().toString());
            //requestInfo.putExtra(DATA, eventInstance);


            requestInfo.putExtra(CHANGED, true);
            if (requestInfo.getBooleanExtra(CREATE_OR_NOT, false)) {
                Pointer.setPoint(eventClipInstance);
            }
        }
    }


    /***
     * 返回键不保存，返回未改动
     */
    @Override
    public void finish() {
        eventClipInstance = null;
        requestInfo.putExtra(CHANGED, false);
        setResult(2, requestInfo);
        super.finish();
    }

}
