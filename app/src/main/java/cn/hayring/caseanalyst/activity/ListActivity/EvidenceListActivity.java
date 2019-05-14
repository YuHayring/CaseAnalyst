package cn.hayring.caseanalyst.activity.ListActivity;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.ValueSetter.EvidenceValueSetter;
import cn.hayring.caseanalyst.bean.Evidence;

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
