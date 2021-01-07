package cn.hayring.caseanalyst.view.casemanager.old;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.view.casemanager.casevaluesetter.EventValueSetter;
import cn.hayring.caseanalyst.domain.Event;
import cn.hayring.caseanalyst.view.MyListActivity;

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

    @Override
    public int getSingleLayoutId() {
        return R.layout.single_background_frame;
    }

}
