package cn.hayring.caseanalyst.activity.ListActivity;

import android.os.Bundle;

import java.util.ArrayList;

import cn.hayring.caseanalyst.activity.ValueSetter.EventValueSetter;
import cn.hayring.caseanalyst.activity.ValueSetter.ValueSetter;
import cn.hayring.caseanalyst.pojo.Event;

public class EventListActivity extends MyListActivity<Event> {

    /***
     * 获得本Activity所对应的元素类型
     * @return
     */
    @Override
    public Class<Event> getTClass() {
        return Event.class;
    }

    /***
     * 获得本Activity所对应的ValueSetter
     * @return
     */
    @Override
    public Class getValueSetterClass() {
        return EventValueSetter.class;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        /*requestInfo = getIntent();
        ArrayList<Event> activeUnits =
                (ArrayList<Event>) requestInfo.getSerializableExtra(ValueSetter.DATA);
        mainItemListAdapter.addAllItem(activeUnits);*/
    }

    /***
     * 按返回键之后的操作
     */
    @Override
    public void finish() {

        //requestInfo.putExtra(ValueSetter.DATA, (ArrayList<Event>) mainItemListAdapter.getItems());
        requestInfo.putExtra(ValueSetter.TYPE, ValueSetter.EVENT_LIST);
        setResult(2, requestInfo);
        super.finish();
    }
}
