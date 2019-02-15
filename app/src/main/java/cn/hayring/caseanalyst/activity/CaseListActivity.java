package cn.hayring.caseanalyst.activity;

import android.os.Bundle;

import cn.hayring.caseanalyst.pojo.Case;

public class CaseListActivity extends MyListActivity<Case> {
    @Override
    public Class<Case> getTClass() {
        return Case.class;
    }

    @Override
    public Class<CaseValueSetter> getValueSetterClass() {
        return CaseValueSetter.class;
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        //原始数据添加
        Case item = cn.hayring.caseanalyst.pojo.PojoInstanceCreater.getConanCase();
        mainItemListAdapter.addItem(item);
    }
}
