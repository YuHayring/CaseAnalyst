package cn.hayring.caseanalyst.activity.ListActivity;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.ValueSetter.OrganizationValueSetter;
import cn.hayring.caseanalyst.bean.Organization;

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

    @Override
    public int getSingleLayoutId() {
        return R.layout.single_head_frame;
    }

}
