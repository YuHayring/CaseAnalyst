package cn.hayring.caseanalyst.activity.ListActivity;

import android.os.Bundle;

import java.util.ArrayList;

import cn.hayring.caseanalyst.activity.ValueSetter.PersonValueSetter;
import cn.hayring.caseanalyst.activity.ValueSetter.ValueSetter;
import cn.hayring.caseanalyst.pojo.Person;

/***
 * 能动单元列表
 */
public class PersonListActivity extends MyListActivity<Person> {

    /***
     * 获得本Activity所对应的元素类型
     * @return
     */
    @Override
    public Class<Person> getTClass() {
        return Person.class;
    }

    /***
     * 获得本Activity所对应的ValueSetter
     * @return
     */
    @Override
    public Class getValueSetterClass() {
        return PersonValueSetter.class;
    }

    /***
     * 生命周期方法
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
/*        requestInfo = getIntent();
        ArrayList<Person> persons =
                (ArrayList<Person>) requestInfo.getSerializableExtra(ValueSetter.DATA);
        mainItemListAdapter.addAllItem(persons);*/
    }

    /***
     * 按返回键之后的操作---------保存
     */
    @Override
    public void finish() {
        //传输参数和数据
        //requestInfo.putExtra(ValueSetter.DATA, (ArrayList<Person>) mainItemListAdapter.getItems());
        requestInfo.putExtra(ValueSetter.TYPE, ValueSetter.PERSON_LIST);
        setResult(2, requestInfo);
        super.finish();
    }


}
