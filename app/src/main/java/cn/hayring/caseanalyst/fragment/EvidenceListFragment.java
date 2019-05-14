package cn.hayring.caseanalyst.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.ValueSetter.EvidenceValueSetter;
import cn.hayring.caseanalyst.bean.Evidence;

public class EvidenceListFragment extends MyListFragment<Evidence> {
    @Override
    public Class getTClass() {
        return Evidence.class;
    }

    @Override
    public int getSingleLayoutId() {
        return R.layout.single_evidence;
    }

    @Override
    public Class getValueSetterClass() {
        return EvidenceValueSetter.class;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(mainActivity, 3);
    }

    @Override
    protected ArrayList<Evidence> getData() {
        return getMainActivity().getCaseInstance().getEvidences();
    }
}
