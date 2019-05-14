package cn.hayring.caseanalyst.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.ValueSetter.OrganizationValueSetter;
import cn.hayring.caseanalyst.bean.Organization;

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
