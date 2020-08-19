package cn.hayring.caseanalyst.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.bean.Case;
import cn.hayring.caseanalyst.bean.Organization;
import cn.hayring.caseanalyst.bean.Person;
import cn.hayring.caseanalyst.bean.Relationship;
import cn.hayring.caseanalyst.listener.FreeMovingListener;
import cn.hayring.caseanalyst.utils.Pointer;
import cn.hayring.caseanalyst.view.Circle;
import cn.hayring.caseanalyst.view.DashArrowV3;
import cn.hayring.caseanalyst.view.HVScrollView;

public class PersonGraphV2 extends AppCompatActivity {

    /***
     * 任务栏和toolbar的高度
     */
    protected int topHeight;


    /***
     * 放置GirdLayout的容器
     */
    protected LinearLayout container;


    /***
     * 二维滑动ScrollView
     */
    protected HVScrollView personGraphRoot;

    /***
     * 案件实例
     */
    protected Case caseInstance;

    /***
     * Person在ViewGroup中的view-id
     */
    protected BiMap<Person, Integer> idSet = HashBiMap.create();
    protected BiMap<Integer, Person> personSet = idSet.inverse();

    //关系面板被选中的人
    Person personTSelected;
    Person personESelected;
    //T是否被选中
    boolean isTSelected = true;

    /***
     * 可添加连接线的ViewGroup
     */
    protected FrameLayout dashArrowRoot;

    /***
     * 连接线是否已经绘制
     */
    protected boolean hasDrawed;

    /***
     * 关系悬浮窗
     */
    protected FrameLayout relationshipWindow;

    /**
     * 头像点击监听器
     */
    private SinglePerosnOnClickListener singlePersonOnClickListener = new SinglePerosnOnClickListener();


    /***
     * 生命周期加载方法
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_graph_v2);
        caseInstance = (Case) Pointer.getPoint();
        initView();
    }

    /***
     * 初始化view
     */
    protected void initView() {
        //注册id
        personGraphRoot = findViewById(R.id.person_graph_v2_scroll);
        container = findViewById(R.id.person_graph_container);
        GridLayout nonOrgPersonGraph = findViewById(R.id.non_org_person_graph);
        dashArrowRoot = findViewById(R.id.dash_arrow_root);
        relationshipWindow = findViewById(R.id.relationship_window);

        //注册移动监听
        relationshipWindow.setOnTouchListener(new FreeMovingListener(relationshipWindow, true));

        //注册点击监听
        relationshipWindow.getChildAt(0).setOnClickListener(view -> {
            isTSelected = true;
            ((TextView) ((FrameLayout) view.getParent()).getChildAt(0)).setTextSize(30);
            ((TextView) ((FrameLayout) view.getParent()).getChildAt(2)).setTextSize(25);
        });
        relationshipWindow.getChildAt(2).setOnClickListener(view -> {
            isTSelected = false;
            ((TextView) ((FrameLayout) view.getParent()).getChildAt(2)).setTextSize(30);
            ((TextView) ((FrameLayout) view.getParent()).getChildAt(0)).setTextSize(25);
        });
        ((TextView) (relationshipWindow).getChildAt(0)).setTextSize(30);
        ((TextView) (relationshipWindow).getChildAt(2)).setTextSize(25);


        //layout加载器
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        //无组织人员图，判断行列数
        int row;
        if (caseInstance.getNonOrgPersons().size() < 4) {
            row = 1;
        } else if (caseInstance.getNonOrgPersons().size() < 9) {
            row = 2;
        } else {
            row = 3;
        }
        //设置布局参数
        nonOrgPersonGraph.setOrientation(GridLayout.VERTICAL);
        nonOrgPersonGraph.setRowCount(row);

        //绘制无组织人关系图
        for (Person a : caseInstance.getNonOrgPersons()) {
            //加载单个人显示信息和头像
            FrameLayout personItem = (FrameLayout) layoutInflater.inflate(R.layout.single_person_graph, null);
            ImageView head = (ImageView) personItem.getChildAt(0);
            TextView nameText = (TextView) personItem.getChildAt(1);
            personItem.setId(View.generateViewId());
            idSet.put(a, personItem.getId());

            //设置头像
            if (a.getImageIndex() != null) {
                try {
                    FileInputStream headIS = openFileInput(a.getImageIndex() + ".jpg");
                    Bitmap image = BitmapFactory.decodeStream(headIS);
                    head.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            //设置名称
            nameText.setText(a.getName());

            //注册点击监听器
            personItem.setOnClickListener(singlePersonOnClickListener);

            //添加
            nonOrgPersonGraph.addView(personItem);
        }

        //按组织绘制关系图
        for (Organization org : caseInstance.getOrganizations()) {
            //计算行列
            int rows;
            if (org.getMembers().size() < 4) {
                rows = 1;
            } else if (org.getMembers().size() < 9) {
                rows = 2;
            } else {
                rows = 3;
            }
            //加载viewgroup，设置布局参数
            GridLayout orgGraph = new GridLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            GridLayout.LayoutParams gridLayoutParams = new GridLayout.LayoutParams(layoutParams);
            gridLayoutParams.setGravity(Gravity.CENTER);
            orgGraph.setLayoutParams(gridLayoutParams);
            orgGraph.setOrientation(GridLayout.VERTICAL);
            orgGraph.setRowCount(rows);

            //按人绘制图
            for (Person a : org.getMembers()) {
                //读取加载数据
                FrameLayout personItem = (FrameLayout) layoutInflater.inflate(R.layout.single_person_graph, null);
                ImageView head = (ImageView) personItem.getChildAt(0);
                TextView nameText = (TextView) personItem.getChildAt(1);

                personItem.setId(View.generateViewId());
                idSet.put(a, personItem.getId());

                //设置头像
                if (a.getImageIndex() != null) {
                    try {
                        FileInputStream headIS = openFileInput(a.getImageIndex() + ".jpg");
                        Bitmap image = BitmapFactory.decodeStream(headIS);
                        head.setImageBitmap(image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                //设置名称，
                nameText.setText(a.getName());

                personItem.setOnClickListener(singlePersonOnClickListener);

                //添加
                orgGraph.addView(personItem);

            }

            //将总图添加进layout栈
            container.addView(orgGraph);
        }

    }


    /***
     * view加载完之后画连接线操作
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //未绘制图则进行绘制
        if (hasFocus && !hasDrawed) {
            //获取高度
            int[] loc = new int[2];
            personGraphRoot.getLocationInWindow(loc);
            topHeight = loc[1];
            //constraintLayout.addView(dashArrow);
            //图的遍历算法类
            GraphLoopTest tool = new GraphLoopTest(caseInstance.getPersons());
            //广度优先画图
            tool.BFSSearch(caseInstance.getPersons().get(0));
            //头像置顶
            container.bringToFront();
        }
    }


    /***
     * 根据view画连接线
     * @param person1
     * @param person2
     */
    public void drawDashArrow(FrameLayout person1, FrameLayout person2, Relationship<Person, Person> relationship) {
        DashArrowV3 dashArrow = new DashArrowV3(this, (Circle) person1.getChildAt(2), (Circle) person2.getChildAt(2), topHeight, personGraphRoot);
        dashArrow.setRelationship(relationship);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int[] margins = dashArrow.getMargin();
        layoutParams.setMargins(margins[0], margins[1], margins[2], margins[3]);
        dashArrow.setLayoutParams(layoutParams);
        //设置点击监听器，显示关系信息
//        dashArrow.setOnClickListener(view -> {
//            TextView tName = (TextView) relationshipWindow.getChildAt(0);
//            TextView key = (TextView) relationshipWindow.getChildAt(1);
//            TextView eName = (TextView) relationshipWindow.getChildAt(2);
//            String tNameText = dashArrow.getRelationship().getItemT().getName();
//            tName.setText(tNameText);
//            String keyText = dashArrow.getRelationship().getKey();
//            key.setText(keyText);
//            String eNameText = dashArrow.getRelationship().getItemE().getName();
//            eName.setText(eNameText);
//
//        });
        dashArrowRoot.addView(dashArrow);
    }


    /**
     * 使用java实现图的图的广度优先 和深度优先遍历算法，遍历Person和Relationship画关系图
     */
    class GraphLoopTest {
        private HashMap<Person, ArrayList<Relationship<Person, Person>>> graph = new HashMap<Person, ArrayList<Relationship<Person, Person>>>();
        private HashMap<Relationship<Person, Person>, Boolean> visited = new HashMap<Relationship<Person, Person>, Boolean>();

        /**
         * 初始化图数据：使用邻居表来表示图数据。
         */
        public GraphLoopTest(ArrayList<Person> persons) {
//        图结构如下
//          1
//        /   \
//       2     3
//      / \   / \
//     4  5  6  7
//      \ | / \ /
//        8    9
            /*graph.put("1", Arrays.asList("2", "3"));
            graph.put("2", Arrays.asList("1", "4", "5"));
            graph.put("3", Arrays.asList("1", "6", "7"));
            graph.put("4", Arrays.asList("2", "8"));
            graph.put("5", Arrays.asList("2", "8"));
            graph.put("6", Arrays.asList("3", "8", "9"));
            graph.put("7", Arrays.asList("3", "9"));
            graph.put("8", Arrays.asList("4", "5", "6"));
            graph.put("9", Arrays.asList("6", "7"));*/

            for (Person a : persons) {
                graph.put(a, a.getManManRelationships());
                for (Relationship<Person, Person> re : a.getManManRelationships()) {
                    visited.put(re, false);
                }
            }
        }

        /**
         * 宽度优先搜索(BFS, Breadth First Search)
         * BFS使用队列(queue)来实施算法过程
         */
        private Queue<Person> queue = new LinkedList<Person>();
        private Map<Person, Boolean> status = new HashMap<Person, Boolean>();

        /**
         * 开始点
         *
         * @param person
         */
        public void BFSSearch(Person person) {
            //1.把起始点放入queue；
            queue.add(person);
            status.put(person, false);
            bfsLoop();
        }

        private void bfsLoop() {
            //  1) 从queue中取出队列头的点；更新状态为已经遍历。
            Person currentQueueHeader = queue.poll(); //出队

            //访问节点
            List<Relationship<Person, Person>> neighborPoints = graph.get(currentQueueHeader);
            for (Relationship<Person, Person> re : neighborPoints) {
                if (!visited.get(re)) {
                    Person nextPerson = re.getItemE().equals(currentQueueHeader) ? re.getItemT() : re.getItemE();


                    //遍历进行的操作
                    FrameLayout that = findViewById(idSet.get(nextPerson));
                    FrameLayout thiss = findViewById(idSet.get(currentQueueHeader));
                    drawDashArrow(that, thiss, re);


                    visited.put(re, true);
                }
            }
            //访问完成
            status.put(currentQueueHeader, true);

            //System.out.println(currentQueueHeader);
            //  2) 找出与此点邻接的且尚未遍历的点，进行标记，然后全部放入queue中。

            for (Relationship<Person, Person> poinit : neighborPoints) {
                Person nextPerson = poinit.getItemE().equals(currentQueueHeader) ? poinit.getItemT() : poinit.getItemE();
                if (!status.getOrDefault(nextPerson, false)) { //未被遍历
                    if (queue.contains(nextPerson)) continue;
                    queue.add(nextPerson);
                    status.put(nextPerson, false);
                }
            }
            if (!queue.isEmpty()) {  //如果队列不为空继续遍历
                bfsLoop();
            }
        }


        /**
         * 深度优先搜索(DFS, Depth First Search)
         * DFS使用队列(queue)来实施算法过程
         * stack具有后进先出LIFO(Last Input First Output)的特性，DFS的操作步骤如下：
         */
//     1、把起始点放入stack；
//     2、重复下述3步骤，直到stack为空为止：
//    从stack中访问栈顶的点；
//    找出与此点邻接的且尚未遍历的点，进行标记，然后全部放入stack中；
//    如果此点没有尚未遍历的邻接点，则将此点从stack中弹出。
/*
        private Stack<Person> stack = new Stack<Person>();
        public void DFSSearch(Person startPoint) {
            stack.push(startPoint);
            status.put(startPoint, true);
            dfsLoop();
        }

        private void dfsLoop() {
            if(stack.empty()){
                return;
            }
            //查看栈顶元素，但并不出栈
            Person stackTopPoint = stack.peek();
            //  2) 找出与此点邻接的且尚未遍历的点，进行标记，然后全部放入queue中。
            List<Relationship<Person,Person>> neighborPoints = graph.get(stackTopPoint);
            for (Relationship<Person,Person> re : neighborPoints) {
                Person nextPerson = re.getItemE().equals(stackTopPoint)?re.getItemT():re.getItemE();
                if (!status.getOrDefault(nextPerson, false)) { //未被遍历
                    stack.push(nextPerson);
                    status.put(nextPerson, true);
                    dfsLoop();
                }
            }
            Person popPoint =  stack.pop();
            //System.out.println(popPoint);
        }*/


    }

    //点击人物，绘制关系面板
    class SinglePerosnOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Person person = personSet.get(view.getId());
            if (isTSelected) {
                TextView tName = (TextView) relationshipWindow.getChildAt(0);
                personTSelected = person;
                tName.setText(person.getName());
            } else {
                TextView eName = (TextView) relationshipWindow.getChildAt(2);
                personESelected = person;
                eName.setText(person.getName());
            }

            TextView key = (TextView) relationshipWindow.getChildAt(1);
            if (personTSelected != null && personESelected != null) {
                if (personTSelected == personESelected) {
                    key.setText(R.string.same_pserson);
                } else {
                    Relationship target = null;
                    //遍历邻接表，有待改进
                    for (Relationship mmR : personTSelected.getManManRelationships()) {
                        if (mmR.getItemE().equals(personESelected)) {
                            target = mmR;
                            break;
                        }
                    }
                    if (null == target) {
                        key.setText(R.string.no_relationship);
                    } else {
                        key.setText(target.getKey());
                    }
                }
            }

        }
    }
}

