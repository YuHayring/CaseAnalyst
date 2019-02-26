package cn.hayring.caseanalyst.activity.ListActivity;

import android.os.Bundle;

import cn.hayring.caseanalyst.activity.ValueSetter.CaseValueSetter;
import cn.hayring.caseanalyst.pojo.Case;

public class CaseListActivity extends MyListActivity<Case> {

    /***
     * 获得本Activity所对应的元素类型
     * @return
     */
    @Override
    public Class<Case> getTClass() {
        return Case.class;
    }

    /***
     * 获得本Activity所对应的ValueSetter
     * @return
     */
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
