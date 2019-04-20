package cn.hayring.caseanalyst.activity.ListActivity;

import cn.hayring.caseanalyst.activity.ValueSetter.EventValueSetter;
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


}
