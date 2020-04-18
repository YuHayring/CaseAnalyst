package cn.hayring.caseanalyst.activity.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.hayring.caseanalyst.activity.ListActivity.MyListActivity;
import cn.hayring.caseanalyst.activity.ValueSetter.ValueSetter;
import cn.hayring.caseanalyst.bean.Case;
import cn.hayring.caseanalyst.bean.Evidence;
import cn.hayring.caseanalyst.bean.Avatars;
import cn.hayring.caseanalyst.bean.Listable;
import cn.hayring.caseanalyst.bean.Organization;
import cn.hayring.caseanalyst.bean.Person;
import cn.hayring.caseanalyst.bean.Relationable;
import cn.hayring.caseanalyst.fragment.MyListFragment;
import cn.hayring.caseanalyst.listener.RecyclerItemDeleteDialogListener;
import cn.hayring.caseanalyst.utils.Pointer;

/***
 * 案件列表设置器
 * @author Hayring
 */
public class MyFragmentListAdapter<T extends Listable> extends RecyclerView.Adapter<ListableViewHolder> {

    /***
     * 名字显示长度限制
     */
    public static final int NAME_CHAR_LENGTH = 7;

    /***
     * 信息显示长度限制
     */
    public static final int INFO_CHAR_LENGTH = 16;


    /***
     * 案件集合，显示数据源
     */
    private List<T> items;

    /***
     * 数据源getter
     */
    public List<T> getItems() {
        return items;
    }

    /***
     * Fragment引用
     */
    private MyListFragment myListFragment;

    /***
     * activity引用
     */
    private Context context;

    /***
     * 输入数据源构造器
     * @param items
     */
    public MyFragmentListAdapter(Context context, MyListFragment myListFragment, List<T> items) {
        this.context = context;
        this.myListFragment = myListFragment;
        this.items = items;
    }


    /***
     * 显示内容的绑定
     * Bind data and view；
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ListableViewHolder holder, int position) {
        T instance = items.get(position);
        //缩减并绑定名称
        String name = instance.getName();
        if (name.length() > NAME_CHAR_LENGTH) {
            name = name.substring(0, NAME_CHAR_LENGTH - 2) + "...";
        }
        holder.name.setText(name);

        //缩减并绑定信息绑定
        if (!(instance instanceof Evidence)) {
            String info = instance.getInfo();
            if (info.length() > INFO_CHAR_LENGTH) {
                info = info.substring(0, INFO_CHAR_LENGTH - 2) + "...";
            }
            holder.info.setText(info);
        }

        //注册点击监听器
        holder.itemView.setOnClickListener(new EditItemListener());
        //holder.itemView.setOnLongClickListener(new DeleteDialogListener());
        holder.itemView.setOnLongClickListener(new deleteItemListener(myListFragment.getMainActivity()));

        if (instance instanceof Avatars) {
            Avatars haveHead = (Avatars) instance;
            ValueSetter.loadHeadImage(haveHead, holder.head, context);
        }
    }


    /***
     * 元素编辑点击监听器
     */
    public class EditItemListener implements View.OnClickListener {


        /***
         * 编辑元素
         * Edit Item
         * @param view
         */
        @Override
        public void onClick(View view) {
            //取出元素
            int position = myListFragment.getItemListRecycler().getChildAdapterPosition(view);
            T item = items.get(position);


            //注册Activity，ValueSetter
            Intent itemTransporter = new Intent(myListFragment.getMainActivity(), myListFragment.getValueSetterClass());


            //绑定参数
            itemTransporter.putExtra(ValueSetter.CREATE_OR_NOT, false);

            Pointer.setPoint(item);
            //itemTransporter.putExtra(ValueSetter.DATA, item);
            //itemTransporter.putExtra(ValueSetter.POSITION, position);

            //启动ValueSetter
            myListFragment.startActivityForResult(itemTransporter, MyListActivity.REQUEST_CODE);

        }
    }


    class deleteItemListener extends RecyclerItemDeleteDialogListener {

        public deleteItemListener(Context context) {
            super(context);
        }

        @Override
        protected void delete(int index) {
            Class clazz = myListFragment.getTClass();

            //若删除的是的有头像的类,删除其图片
            if (clazz == Evidence.class || clazz == Person.class || clazz == Organization.class) {
                Avatars instance = (Avatars) items.get(index);
                if (instance.getImageIndex() != null) {
                    myListFragment.getMainActivity().deleteFile(instance.getImageIndex() + ".jpg");
                }
            }
            Listable listable = items.get(index);
            if (!(listable instanceof Case)) {
                Relationable relationable = (Relationable) listable;
                relationable.removeSelf();
            }
            deleteItem(index);
        }
    }


    /***
     *
     * @return count 总数
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /***
     * Layout绑定
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ListableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //注册单个元素的layout
        View v;
        v = LayoutInflater.from(myListFragment.getMainActivity()).inflate(myListFragment.getSingleLayoutId(), parent, false);
        return new ListableViewHolder(v);
    }


    //改变数据源的四个方法


    public void addItem(T item) {
        items.add(item);
        ////更新数据集不是用adapter.notifyDataSetChanged()而是notifyItemInserted(position)与notifyItemRemoved(position) 否则没有动画效果。
        notifyItemInserted(items.size() - 1);
    }

    public void setItem(int position, T item) {
        items.set(position, item);
        notifyItemChanged(position);
    }

    public void deleteItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void addAllItem(ArrayList<T> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }
}
