package cn.hayring.caseanalyst.view.magic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.domain.Person;
import cn.hayring.caseanalyst.domain.Relationship;
import cn.hayring.caseanalyst.utils.Pointer;
import cn.hayring.caseanalyst.view.myview.Circle;
import cn.hayring.caseanalyst.view.myview.CustumViewGroup;
import cn.hayring.caseanalyst.view.myview.DashArrow;
import cn.hayring.caseanalyst.view.listener.PersonItemOnTouchListener;

/***
 * 人物关系图Activity
 */
public class PersonGraph extends AppCompatActivity {

    /***
     * 任务栏和toolbar的高度
     */
    protected int topHeight;

    /***
     * 申请来的人物数据源
     */
    private ArrayList<Person> persons;

    /***
     * 图所在的布局
     */
    protected LinearLayout linearLayout;

    /***
     * 用来计算高度的布局
     */
    FrameLayout frameLayout;

    /***
     * 图所在的自定义ViewGroup
     */
    CustumViewGroup custumViewGroup;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_graph);

        //注册控件
        linearLayout = findViewById(R.id.person_graph_v2_scroll);
        persons = (ArrayList<Person>) Pointer.getPoint();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        frameLayout = (FrameLayout) findViewById(R.id.person_graph_layout);
        custumViewGroup = new CustumViewGroup(PersonGraph.this, width);
        //custumViewGroup.setBackgroundColor(Color.parseColor("#888888"));
        /*for (int i = 0; i <20; i++) {
         *//*
            button=new Button(this);
            String srt=messages;
            message=srt.substring(0,random.nextInt(10))+i;
            button.setText(message);
            custumViewGroup.addView(button);*//*

            LayoutInflater inflater = getLayoutInflater();
            FrameLayout personItem= (FrameLayout) inflater.inflate(R.layout.single_person_graph, null);
            personItem.setOnTouchListener(new PersonItemOnTouchListener(personItem));
            custumViewGroup.addView(personItem);
        }*/
        LayoutInflater inflater = getLayoutInflater();
        for (Person a : persons) {
            FrameLayout personItem = (FrameLayout) inflater.inflate(R.layout.single_person_graph, null);
            ImageView head = (ImageView) personItem.getChildAt(0);
            TextView nameText = (TextView) personItem.getChildAt(1);

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

            nameText.setText(a.getName());
            personItem.setOnTouchListener(new PersonItemOnTouchListener(personItem));
            custumViewGroup.addView(personItem);
        }

        frameLayout.addView(custumViewGroup);
    }

    /***
     * 根据view画连接线
     * @param person1
     * @param person2
     */
    public void drawDashArrow(FrameLayout person1, FrameLayout person2) {
        DashArrow dashArrow = new DashArrow(this, (Circle) person1.getChildAt(2), (Circle) person2.getChildAt(2), topHeight);
        frameLayout.addView(dashArrow);
    }

    /***
     * 根据人物在列表中的index画连接线
     * @param a
     * @param b
     */
    public void drawDashArrow(int a, int b) {
        drawDashArrow((FrameLayout) custumViewGroup.getChildAt(a), (FrameLayout) custumViewGroup.getChildAt(b));
    }


    /***
     * view加载完之后画连接线操作
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //获取高度
        int[] loc = new int[2];
        linearLayout.getLocationInWindow(loc);
        topHeight = loc[1];
        //constraintLayout.addView(dashArrow);
        //图的遍历算法类
        GraphLoopTest tool = new GraphLoopTest(persons);
        //广度优先画图
        tool.BFSSearch(persons.get(0));
        //头像置顶
        custumViewGroup.bringToFront();
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
                    int thisIndex = persons.indexOf(currentQueueHeader);
                    int thatIndex = persons.indexOf(nextPerson);
                    drawDashArrow(thatIndex, thisIndex);
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
}
