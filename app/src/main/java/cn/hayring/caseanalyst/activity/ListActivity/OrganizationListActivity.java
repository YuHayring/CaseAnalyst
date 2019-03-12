package cn.hayring.caseanalyst.activity.ListActivity;

import android.os.Bundle;

import java.util.ArrayList;

import cn.hayring.caseanalyst.activity.ValueSetter.OrganizationValueSetter;
import cn.hayring.caseanalyst.activity.ValueSetter.PersonValueSetter;
import cn.hayring.caseanalyst.activity.ValueSetter.ValueSetter;
import cn.hayring.caseanalyst.pojo.Organization;

/***
 * 能动单元列表
 */
public class OrganizationListActivity extends MyListActivity<Organization> {

    /***
     * 获得本Activity所对应的元素类型
     * @return
     */
    @Override
    public Class<Organization> getTClass() {
        return Organization.class;
    }

    /***
     * 获得本Activity所对应的ValueSetter
     * @return
     */
    @Override
    public Class getValueSetterClass() {
        return OrganizationValueSetter.class;
    }

    /***
     * 生命周期方法
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestInfo = getIntent();
        ArrayList<Organization> organizations =
                (ArrayList<Organization>) requestInfo.getSerializableExtra(ValueSetter.DATA);
        mainItemListAdapter.addAllItem(organizations);
    }

    /***
     * 按返回键之后的操作---------不保存
     */
    @Override
    public void finish() {
        //传输参数和数据
        requestInfo.putExtra(ValueSetter.DATA, (ArrayList<Organization>) mainItemListAdapter.getItems());
        requestInfo.putExtra(ValueSetter.TYPE, ValueSetter.ORG_LIST);
        setResult(2, requestInfo);
        super.finish();
    }


}
