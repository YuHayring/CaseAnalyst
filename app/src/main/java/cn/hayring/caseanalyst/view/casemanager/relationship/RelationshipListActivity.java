package cn.hayring.caseanalyst.view.casemanager.relationship;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.view.ValueSetter;
import cn.hayring.caseanalyst.domain.Relationable;
import cn.hayring.caseanalyst.domain.Relationship;
import cn.hayring.caseanalyst.utils.Pointer;

public class RelationshipListActivity<T extends Relationship> extends AppCompatActivity {

    protected int relationshipType;


    /***
     * 关系设置窗口的入口元素
     */
    Relationable connector;

    /***
     * 请求传输者
     */
    protected Intent requestInfo;


    public static final int REQUESTCODE = 1;


    Toolbar toolbar;

    /***
     * 添加元素按钮
     */
    FloatingActionButton createRelationshipButton;
    /***
     * 列表View
     */
    RecyclerView relationshipListRecycler;
    /***
     * 列表适配器
     */
    RelationshipListAdapter<T> mainRelationshipListAdapter;

    public RecyclerView getRelationshipListRecycler() {
        return relationshipListRecycler;
    }

    /***
     * 必须再次重写以补充初始化后的操作
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //注册
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createRelationshipButton = findViewById(R.id.add_item_button);
        createRelationshipButton.setOnClickListener(new CreateNewRelationshipListener());
        //初始化数据源
        List<T> relationships = (ArrayList) Pointer.getPoint();
        //绑定数据源
        relationshipListRecycler = findViewById(R.id.recycler_list);
        relationshipListRecycler.setLayoutManager(new LinearLayoutManager(this));
        relationshipListRecycler.setItemAnimator(new DefaultItemAnimator());
        mainRelationshipListAdapter = new RelationshipListAdapter(this, relationships);


        relationshipListRecycler.setAdapter(mainRelationshipListAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        relationshipListRecycler.addItemDecoration(dividerItemDecoration);


        requestInfo = getIntent();
        //ArrayList<T> relationshipSources = (ArrayList) Pointer.getPoint();
        connector = Pointer.getConnector();
        relationshipType = requestInfo.getIntExtra(ValueSetter.RELATIONSHIP_TYPE, -1);
        //mainRelationshipListAdapter.addAllItem(relationshipSources);


    }

    /***
     * 新元素点击监听器
     */
    class CreateNewRelationshipListener implements View.OnClickListener {

        /***
         * 创建新元素
         * Create new Item
         * @param view
         */
        @Override
        public void onClick(View view) {

            Intent relationshipTransporter = new Intent(RelationshipListActivity.this, RelationshipValueSetter.class);
            //行为:新建数据行为
            relationshipTransporter.putExtra(ValueSetter.CREATE_OR_NOT, true);

            //发送关联者
            //relationshipTransporter.putExtra(ValueSetter.CONNECTOR, connector);
            Pointer.setConnector(connector);

            //类型设置
            relationshipTransporter.putExtra(ValueSetter.RELATIONSHIP_TYPE, relationshipType);


            //启动新Activity
            startActivityForResult(relationshipTransporter, REQUESTCODE);

        }
    }


    /***
     * 编辑完成调用
     * @author Hayring
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent relationshipTransporter) {
        super.onActivityResult(requestCode, resultCode, relationshipTransporter);

        //未改变就结束
        if (!relationshipTransporter.getBooleanExtra(ValueSetter.CHANGED, true)) {
            return;
        }

        if (relationshipTransporter.getBooleanExtra(ValueSetter.CREATE_OR_NOT, false)) {
            //新元素
            //T newRelationship = (T) relationshipTransporter.getSerializableExtra(ValueSetter.DATA);

            T newRelationship = (T) Pointer.getPoint();

            //内部已实现Notificate UI 变化
            mainRelationshipListAdapter.addItem(newRelationship);
        } else if (!relationshipTransporter.getBooleanExtra(ValueSetter.CREATE_OR_NOT, true)) {
            //修改元素
            /*int position = relationshipTransporter.getIntExtra(ValueSetter.POSITION, 0);
            T newRelationship = (T) relationshipTransporter.getSerializableExtra(ValueSetter.DATA);
            mainRelationshipListAdapter.setItem(position, newRelationship);*/


            mainRelationshipListAdapter.notifyDataSetChanged();
        }


    }

    /***
     * 按返回键之后-----保存
     */
    @Override
    public void finish() {
        //传输参数和数据
        //requestInfo.putExtra(ValueSetter.DATA, (ArrayList<Relationship>) mainRelationshipListAdapter.getRelationships());
        setResult(2, requestInfo);
        super.finish();
    }

    public Relationable getConnector() {
        return connector;
    }

    public RelationshipListAdapter<T> getMainRelationshipListAdapter() {
        return mainRelationshipListAdapter;
    }


    public int getRelationshipType() {
        return relationshipType;
    }
}
