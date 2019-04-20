package cn.hayring.caseanalyst.activity.ListActivity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.PersonGraph;
import cn.hayring.caseanalyst.activity.ValueSetter.PersonValueSetter;
import cn.hayring.caseanalyst.pojo.Person;
import cn.hayring.caseanalyst.utils.Pointer;

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
     * 注册菜单方法
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.person_list_menu, menu);
        return true;
    }

    /***
     * 菜单点击监听器
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //设置人物数据
        Pointer.setPoint((ArrayList) mainItemListAdapter.getItems());
        //绘制关系图
        startActivity(new Intent(this, PersonGraph.class));

        return super.onOptionsItemSelected(item);
    }


}
