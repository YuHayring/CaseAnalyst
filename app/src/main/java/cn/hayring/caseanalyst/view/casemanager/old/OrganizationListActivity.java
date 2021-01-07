package cn.hayring.caseanalyst.view.casemanager.old;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.view.casemanager.casevaluesetter.OrganizationValueSetter;
import cn.hayring.caseanalyst.domain.Organization;
import cn.hayring.caseanalyst.view.MyListActivity;

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
