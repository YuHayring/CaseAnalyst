package cn.hayring.caseanalyst.view.casemanager;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.view.casemanager.casevaluesetter.EventValueSetter;
import cn.hayring.caseanalyst.domain.Event;
import cn.hayring.caseanalyst.view.MyListFragment;

public class EventListFragment extends MyListFragment<Event> {
    @Override
    public Class<Event> getTClass() {
        return Event.class;
    }

    @Override
    public int getSingleLayoutId() {
        return R.layout.single_event;
    }

    @Override
    public Class getValueSetterClass() {
        return EventValueSetter.class;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    @Override
    protected ArrayList<Event> getData() {
        return getMainActivity().getCaseInstance().getEvents();
    }
}
