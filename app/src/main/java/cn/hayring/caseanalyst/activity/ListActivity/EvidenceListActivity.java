package cn.hayring.caseanalyst.activity.ListActivity;

import cn.hayring.caseanalyst.activity.ValueSetter.EvidenceValueSetter;
import cn.hayring.caseanalyst.activity.ValueSetter.ValueSetter;
import cn.hayring.caseanalyst.pojo.Evidence;

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


    /***
     * 按返回键之后的操作
     */
    @Override
    public void finish() {

        //requestInfo.putExtra(ValueSetter.DATA, (ArrayList<Evidence>) mainItemListAdapter.getItems());
        requestInfo.putExtra(ValueSetter.TYPE, ValueSetter.EVIDENCE_LIST);
        setResult(2, requestInfo);
        super.finish();
    }


}
