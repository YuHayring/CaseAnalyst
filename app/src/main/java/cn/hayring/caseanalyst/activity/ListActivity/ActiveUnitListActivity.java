package cn.hayring.caseanalyst.activity.ListActivity;

import android.os.Bundle;

import java.util.ArrayList;

import cn.hayring.caseanalyst.activity.ValueSetter.PersonValueSetter;
import cn.hayring.caseanalyst.activity.ValueSetter.ValueSetter;
import cn.hayring.caseanalyst.pojo.ActiveUnit;

/***
 * 能动单元列表
 */
public class ActiveUnitListActivity extends MyListActivity<ActiveUnit> {

    /***
     * 获得本Activity所对应的元素类型
     * @return
     */
    @Override
    public Class<ActiveUnit> getTClass() {
        return ActiveUnit.class;
    }

    /***
     * 获得本Activity所对应的ValueSetter
     * @return
     */
    @Override
    public Class getValueSetterClass() {
        return null;
    }

    /***
     * 生命周期方法
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestInfo = getIntent();
        ArrayList<ActiveUnit> activeUnits =
                (ArrayList<ActiveUnit>) requestInfo.getSerializableExtra(ValueSetter.DATA);
        mainItemListAdapter.addAllItem(activeUnits);
    }

    /***
     * 按返回键之后的操作---------不保存
     */
    @Override
    public void finish() {
        //传输参数和数据
        requestInfo.putExtra(ValueSetter.DATA, (ArrayList<ActiveUnit>) mainItemListAdapter.getItems());
        requestInfo.putExtra(ValueSetter.TYPE, ValueSetter.ACTIVE_UNIT_LIST);
        setResult(2, requestInfo);
        super.finish();
    }


}
