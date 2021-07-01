package cn.hayring.caseanalyst.view.casemanager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.view.casemanager.casevaluesetter.OrganizationValueSetter;
import cn.hayring.caseanalyst.domain.Organization;
import cn.hayring.caseanalyst.view.MyListFragment;

public class OrgListFragment extends MyListFragment<Organization> {
    @Override
    public Class<Organization> getTClass() {
        return Organization.class;
    }

    @Override
    public int getSingleLayoutId() {
        return R.layout.single_head_frame;
    }

    @Override
    public Class getValueSetterClass() {
        return OrganizationValueSetter.class;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(mainActivity);
    }

    @Override
    protected ArrayList<Organization> getData() {
        return getMainActivity().getCaseInstance().getOrganizations();
    }
}
