package cn.hayring.caseanalyst.view.magic;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.view.casemanager.casevaluesetter.EventClipValueSetter;
import cn.hayring.caseanalyst.view.ValueSetter;
import cn.hayring.caseanalyst.domain.EventClip;
import cn.hayring.caseanalyst.utils.Pointer;

public class TimeAxis extends AppCompatActivity {

    RecyclerView recyclerView;

    TimeAxisAdapter adapter;


    /***
     * 添加元素按钮
     */
    FloatingActionButton createItemButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_axis);
        initView();
        initData();
    }

    //添加数据
    private void initData() {
        ArrayList<EventClip> eventClips = (ArrayList<EventClip>) Pointer.getPoint();
        //设置adapter
        adapter = new TimeAxisAdapter(this, eventClips);
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.time_axis_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        //设置方向
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        //设置动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        createItemButton = findViewById(R.id.add_event_clip_button);
        createItemButton.setOnClickListener(new CreateNewItemListener());

    }


    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public TimeAxisAdapter getAdapter() {
        return adapter;
    }

    /***
     * 编辑完成调用
     * @author Hayring
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent itemTransporter) {
        super.onActivityResult(requestCode, resultCode, itemTransporter);

        //未改变就结束
        if (!itemTransporter.getBooleanExtra(ValueSetter.CHANGED, true)) {
            return;
        }

        if (itemTransporter.getBooleanExtra(ValueSetter.CREATE_OR_NOT, false)) {
            //新元素
            //T newItem = (T) itemTransporter.getSerializableExtra(ValueSetter.DATA);
            EventClip newItem = (EventClip) Pointer.getPoint();
            //内部已实现Notificate UI 变化
            adapter.addItem(newItem);
        } else if (!itemTransporter.getBooleanExtra(ValueSetter.CREATE_OR_NOT, true)) {
            adapter.notifyDataSetChanged();
        }


    }


    /***
     * 新元素点击监听器
     */
    class CreateNewItemListener implements View.OnClickListener {

        /***
         * 创建新元素
         * Create new Item
         * @param view
         */
        @Override
        public void onClick(View view) {

            Intent itemTransporter = new Intent(TimeAxis.this, EventClipValueSetter.class);
            //行为:新建数据行为
            itemTransporter.putExtra(ValueSetter.CREATE_OR_NOT, true);
            //启动新Activity
            startActivityForResult(itemTransporter, 0);

        }
    }

}
