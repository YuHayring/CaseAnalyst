package cn.hayring.caseanalyst.activity.adapter;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.MyListActivity;
import cn.hayring.caseanalyst.activity.ValueSetter;
import cn.hayring.caseanalyst.pojo.Listable;

/***
 * 案件列表设置器
 * @author Hayring
 */
public class MyListAdapter<T extends Listable> extends RecyclerView.Adapter<MyListAdapter.VH> {
    /***
     * holder对应两个TextView
     * holder include two TextView
     * @author Hayring
     */
    public static class VH extends RecyclerView.ViewHolder {
        public final TextView name;
        public final TextView info;

        public VH(View v) {
            super(v);
            name = v.findViewById(R.id.item_list_name_item);
            info = v.findViewById(R.id.item_list_info_item);
        }
    }


    /***
     * 案件集合
     */
    private List<T> items;

    public List<T> getItems() {
        return items;
    }

    private MyListActivity mActivity;

    /***
     * 输入案件
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
    public void onBindViewHolder(VH holder, int position) {
        holder.name.setText(items.get(position).getName());
        String info = items.get(position).getInfo();
        if (info.length() > 16) {
            info = info.substring(0, 14) + "...";
        }
        holder.info.setText(info);

        holder.itemView.setOnClickListener(new EditItemListener());
    }


    /***
     * 案件编辑点击监听器
     */
    public class EditItemListener implements View.OnClickListener {


        /***
         * 编辑案件
         * Edit Item
         * @param view
         */
        @Override
        public void onClick(View view) {


            Intent itemTransporter = new Intent(mActivity, mActivity.getValueSetterClass());

            int position = mActivity.getItemListRecycler().getChildAdapterPosition(view);

            T item = items.get(position);

            //行为:修改数据行为
            itemTransporter.putExtra(ValueSetter.CREATE_OR_NOT, false);
            //绑定Item
            itemTransporter.putExtra(ValueSetter.DATA, item);
            //点击位置
            itemTransporter.putExtra(ValueSetter.POSITION, position);
            mActivity.startActivityForResult(itemTransporter, MyListActivity.REQUESTCODE);

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
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(mActivity).inflate(R.layout.single_item_list_frame, parent, false);
        return new VH(v);
    }

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
