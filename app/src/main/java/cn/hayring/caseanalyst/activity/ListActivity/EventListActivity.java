package cn.hayring.caseanalyst.activity.ListActivity;

import cn.hayring.caseanalyst.activity.ValueSetter.EventValueSetter;
import cn.hayring.caseanalyst.activity.ValueSetter.ValueSetter;
import cn.hayring.caseanalyst.pojo.Event;

/***
 * 事件列表Activity
 */
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
