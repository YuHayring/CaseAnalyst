package cn.hayring.caseanalyst.view.casemanager.old;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.view.casemanager.casevaluesetter.EvidenceValueSetter;
import cn.hayring.caseanalyst.domain.Evidence;
import cn.hayring.caseanalyst.view.MyListActivity;

public class EvidenceListActivity extends MyListActivity<Evidence> {

    /***
     * 获得本Activity所对应的元素类型
     * @return
     */
    @Override
    public Class<Evidence> getTClass() {
        return Evidence.class;
    }

    /***
     * 获得本Activity所对应的ValueSetter
     * @return
     */
    @Override
    public Class getValueSetterClass() {
        return EvidenceValueSetter.class;
    }

    @Override
    public int getSingleLayoutId() {
        return R.layout.single_head_frame;
    }


}
