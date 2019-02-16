package cn.hayring.caseanalyst.activity;

import android.os.Bundle;

import java.util.ArrayList;

import cn.hayring.caseanalyst.activity.MyListActivity;
import cn.hayring.caseanalyst.pojo.ActiveUnit;

public class ActiveUnitListActivity extends MyListActivity<ActiveUnit> {
    @Override
    public Class<ActiveUnit> getTClass() {
        return ActiveUnit.class;
    }

    @Override
    public Class getValueSetterClass() {
        return PersonValueSetter.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestInfo = getIntent();
        ArrayList<ActiveUnit> activeUnits =
                (ArrayList<ActiveUnit>) requestInfo.getSerializableExtra(ValueSetter.DATA);
        mainItemListAdapter.addAllItem(activeUnits);
    }

    @Override
    public void finish() {

        requestInfo.putExtra(ValueSetter.DATA, (ArrayList<ActiveUnit>) mainItemListAdapter.getItems());
        requestInfo.putExtra(ValueSetter.TYPE, ValueSetter.ACTIVE_UNIT_LIST);
        setResult(2, requestInfo);
        super.finish();

    }
}
