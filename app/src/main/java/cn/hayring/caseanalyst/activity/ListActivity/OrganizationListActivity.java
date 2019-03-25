package cn.hayring.caseanalyst.activity.ListActivity;

import cn.hayring.caseanalyst.activity.ValueSetter.OrganizationValueSetter;
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
     * 按返回键之后的操作---------保存
     */
    @Override
    public void finish() {
        //传输参数和数据
        //requestInfo.putExtra(ValueSetter.DATA, (ArrayList<Organization>) mainItemListAdapter.getItems());
        requestInfo.putExtra(ValueSetter.TYPE, ValueSetter.ORG_LIST);
        setResult(2, requestInfo);
        super.finish();
    }


}
