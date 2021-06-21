package cn.hayring.caseanalyst.view;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.hayring.caseanalyst.domain.Avatars;
import cn.hayring.caseanalyst.domain.Case;
import cn.hayring.caseanalyst.domain.Evidence;
import cn.hayring.caseanalyst.domain.Listable;
import cn.hayring.caseanalyst.domain.Organization;
import cn.hayring.caseanalyst.domain.Person;
import cn.hayring.caseanalyst.domain.Relationable;
import cn.hayring.caseanalyst.utils.Pointer;
import cn.hayring.caseanalyst.view.listener.RecyclerItemDeleteDialogListener;

/***
 * 案件列表设置器
 * @author Hayring
 */
public class MyListAdapter<T extends Listable> extends RecyclerView.Adapter<ListableViewHolder> {

    /***
     * 名字显示长度限制
     */
    public static final int NAME_CHAR_LENGTH = 11;

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
        //holder.itemView.setOnLongClickListener(new DeleteDialogListener());
        holder.itemView.setOnLongClickListener(new deleteItemListener(mActivity));
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
            mActivity.startActivityForResult(itemTransporter, MyListActivity.REQUEST_CODE);

        }
    }

    /***
     * 元素删除监听器
     */
    class deleteItemListener extends RecyclerItemDeleteDialogListener {

        public deleteItemListener(Context context) {
            super(context);
        }

        @Override
        protected void delete(int index) {
            Class clazz = mActivity.getTClass();

            //若删除的是的有头像的类,删除其图片
            if (clazz == Evidence.class || clazz == Person.class || clazz == Organization.class) {
                Avatars instance = (Avatars) items.get(index);
                if (instance.getImageIndex() != null) {
                    mActivity.deleteFile(instance.getImageIndex() + ".jpg");
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
        v = LayoutInflater.from(mActivity).inflate(mActivity.getSingleLayoutId(), parent, false);
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

    public void addAllItem(List<T> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }
}
