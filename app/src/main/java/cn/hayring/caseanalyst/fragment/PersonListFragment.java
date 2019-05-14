package cn.hayring.caseanalyst.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.PersonGraphV2;
import cn.hayring.caseanalyst.activity.ValueSetter.PersonValueSetter;
import cn.hayring.caseanalyst.bean.Person;
import cn.hayring.caseanalyst.utils.Pointer;

public class PersonListFragment extends MyListFragment<Person> {


    @Override
    public Class<Person> getTClass() {
        return Person.class;
    }

    @Override
    public int getSingleLayoutId() {
        return R.layout.single_head_frame;
    }

    @Override
    public Class getValueSetterClass() {
        return PersonValueSetter.class;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(mainActivity);
    }

    @Override
    protected ArrayList<Person> getData() {
        return getMainActivity().getCaseInstance().getPersons();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        menu.add("查看人物关系图");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //设置人物数据
        Pointer.setPoint(mainActivity.getCaseInstance());
        //绘制关系图
        startActivity(new Intent(mainActivity, PersonGraphV2.class));
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        setHasOptionsMenu(true);
    }
}
