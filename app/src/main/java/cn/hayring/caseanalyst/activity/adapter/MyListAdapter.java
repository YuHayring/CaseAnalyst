package cn.hayring.caseanalyst.activity.adapter;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.ListActivity.MyListActivity;
import cn.hayring.caseanalyst.activity.ValueSetter.ValueSetter;
import cn.hayring.caseanalyst.pojo.Evidence;
import cn.hayring.caseanalyst.pojo.Listable;
import cn.hayring.caseanalyst.pojo.Organization;
import cn.hayring.caseanalyst.pojo.Person;
import cn.hayring.caseanalyst.pojo.Relationable;
import cn.hayring.caseanalyst.utils.Pointer;

/***
 * 案件列表设置器
 * @author Hayring
 */
public class MyListAdapter<T extends Listable> extends RecyclerView.Adapter<ListableViewHolder> {

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
     * Activity引用
     */
    private MyListActivity mActivity;

    /***
     * 输入数据源构造器
     * @param items
     */
    public MyListAdapter(MyListActivity mActivity, List<T> items) {
        this.mActivity = mActivity;
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
        //缩减并绑定名称
        String name = items.get(position).getName();
        if (name.length() > NAME_CHAR_LENGTH) {
            name = name.substring(0, NAME_CHAR_LENGTH - 2) + "...";
        }
        holder.name.setText(name);

        //缩减并绑定信息绑定
        String info = items.get(position).getInfo();
        if (info.length() > INFO_CHAR_LENGTH) {
            info = info.substring(0, INFO_CHAR_LENGTH - 2) + "...";
        }
        holder.info.setText(info);

        //注册点击监听器
        holder.itemView.setOnClickListener(new EditItemListener());


        List<String> stringList = new ArrayList<String>();
        stringList.add("删除");

        holder.itemView.setOnLongClickListener(new DeleteDialogListener());
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
            int position = mActivity.getItemListRecycler().getChildAdapterPosition(view);
            T item = items.get(position);


            //注册Activity，ValueSetter
            Intent itemTransporter = new Intent(mActivity, mActivity.getValueSetterClass());


            //绑定参数
            itemTransporter.putExtra(ValueSetter.CREATE_OR_NOT, false);

            Pointer.setPoint(item);
            //itemTransporter.putExtra(ValueSetter.DATA, item);
            //itemTransporter.putExtra(ValueSetter.POSITION, position);

            //启动ValueSetter
            mActivity.startActivityForResult(itemTransporter, MyListActivity.REQUESTCODE);

        }
    }

    /***
     * 元素删除监听器
     */
    class DeleteDialogListener implements View.OnLongClickListener {


        @Override
        public boolean onLongClick(View view) {
            AlertDialog alertDialog = new AlertDialog.Builder(mActivity)
                    .setTitle("警告！")
                    .setMessage("是否要删除？")
                    //.setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton("确定", new ConfirmDeleteItemListener(view))

                    .setNegativeButton("取消", null)
                    /*
                    .setNeutralButton("普通按钮", new DialogInterface.OnClickListener() {//添加普通按钮
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(AlertDialogActivity.this, "---info----", Toast.LENGTH_SHORT).show();
                        }
                    })*/
                    .create();
            alertDialog.show();
            return true;
        }
    }

    /***
     * 确认删除监听器
     */
    class ConfirmDeleteItemListener implements DialogInterface.OnClickListener {//添加"Yes"按钮
        View view;

        public ConfirmDeleteItemListener(View view) {
            super();
            this.view = view;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            int position = mActivity.getItemListRecycler().getChildAdapterPosition(view);
            Class clazz = mActivity.getTClass();

            //若删除的是的有头像的类,删除其图片
            if (clazz == Evidence.class || clazz == Person.class || clazz == Organization.class) {
                Relationable instance = (Relationable) items.get(position);
                if (instance.getImageIndex() != null) {
                    mActivity.deleteFile(instance.getImageIndex() + ".jpg");
                }
            }
            deleteItem(position);
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
        View v = LayoutInflater.from(mActivity).inflate(R.layout.single_item_list_frame, parent, false);
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
